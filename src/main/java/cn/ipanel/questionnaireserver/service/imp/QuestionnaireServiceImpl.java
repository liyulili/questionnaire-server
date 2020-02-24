package cn.ipanel.questionnaireserver.service.imp;

import cn.ipanel.questionnaireserver.exception.CheckedException;
import cn.ipanel.questionnaireserver.mapper.QuestionMapper;
import cn.ipanel.questionnaireserver.mapper.QuestionnaireMapper;
import cn.ipanel.questionnaireserver.mapper.RecordMapper;
import cn.ipanel.questionnaireserver.pojo.Question;
import cn.ipanel.questionnaireserver.pojo.Questionnaire;
import cn.ipanel.questionnaireserver.service.IQuestionnaireService;
import cn.ipanel.questionnaireserver.util.Constant;
import cn.ipanel.questionnaireserver.util.IdWorker;
import cn.ipanel.questionnaireserver.vo.R;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class QuestionnaireServiceImpl implements IQuestionnaireService {

    @Autowired
    QuestionnaireMapper questionnaireMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    IdWorker idWorker;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");


    @Transactional
    @Override
    public R addQuestionnaire(String title, String description, String startTime, String endTime) {

        long id = addQuestionnaireToDataBase(title, description, startTime, endTime);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        return R.ok(jsonObject);
    }

    @Transactional
    @Override
    public R addQuestionnaire(String title, String description, List<Question> questionList, String startTime, String endTime) {

        //添加问卷到数据库返回问卷id
        long id = addQuestionnaireToDataBase(title, description, startTime, endTime);

        //解析Question
        questionList.forEach(question -> {
            question.setQuestionnaireId(id);
            int insert = questionMapper.insert(question);
            if (insert != 1) {
                throw new CheckedException("插入数据库失败");
            }
        });
        return R.ok();
    }

    @Transactional
    @Override
    public R releaseQuestionnaire(Long questionnaireId) {
        List<Questionnaire> questionnaireList = questionnaireMapper.selectList(new QueryWrapper<Questionnaire>().lambda().eq(Questionnaire::getId, questionnaireId));
        if (questionnaireList.isEmpty()) {
            return R.error("未查询到相关问卷");
        }
        int update = questionnaireMapper.update(questionnaireList.get(0), new UpdateWrapper<Questionnaire>().lambda().set(Questionnaire::getStatus, Constant.QUESTIONNAIRE_STATUS_RELEASE));
        if (update != 1) {
            throw new CheckedException("更新失败");
        }

        return R.ok();
    }

    @Transactional
    @Override
    public R deleteQuestionnaire(Long questionnaireId) {

        //1.先删除问卷
        int deleteQuestionnaire = questionnaireMapper.deleteById(questionnaireId);

        //2,3可以考虑异步编程

        //2.删除问题和统计
        HashMap<String, Object> map = new HashMap<>(1);
        map.put(Question.COL_QUESTIONNAIRE_ID, questionnaireId);
        List<Long> questionIdList = questionMapper.selectObjs(new QueryWrapper<Question>().lambda().eq(Question::getQuestionnaireId, questionnaireId).select(Question::getId))
                .stream().map(i -> Long.valueOf(String.valueOf(i))).collect(toList());
        int i = questionMapper.deleteBatchIds(questionIdList);

        //3.删除答题记录
        int i1 = recordMapper.deleteByQuestionnaireIdIn(questionIdList);
        return R.ok();
    }

    @Override
    public R queryQuestionnaire(String startTime, String endTime, Integer status, Integer sortType) {

        List<Questionnaire> questionnaireList = questionnaireMapper.selectList(
                new QueryWrapper<Questionnaire>().lambda()
                        .eq(status != null, Questionnaire::getStatus, status)
                        .ge(StringUtils.isNotBlank(startTime), Questionnaire::getStartTime, LocalDateTime.parse(startTime, formatter))
                        .le(StringUtils.isNotBlank(endTime), Questionnaire::getEndTime, LocalDateTime.parse(endTime, formatter))
                        .orderByAsc(sortType == 1, Questionnaire::getCreateTime)
                        .orderByDesc(sortType == 2, Questionnaire::getCreateTime));

        return R.ok(questionnaireList);
    }

    @Override
    public R queryQuestion(Long questionnaireId) {

        List<Question> questionList = questionMapper.selectList(new QueryWrapper<Question>().lambda().eq(questionnaireId != null, Question::getQuestionnaireId, questionnaireId));

        return R.ok(questionList);
    }

    /**
     * 新建问卷到数据库，返回问卷id
     *
     * @param title       问卷标题
     * @param description 问卷描述
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 问卷id
     */
    private long addQuestionnaireToDataBase(String title, String description, String startTime, String endTime) {
        long id = idWorker.nextId();

        Questionnaire questionnaire = new Questionnaire()
                .setId(id)
                .setTitle(title)
                .setDescription(description)
                .setStatus(Constant.QUESTIONNAIRE_STATUS_UN_RELEASE)
                .setCreateTime(LocalDateTime.now())
                .setStartTime(LocalDateTime.parse(startTime, formatter))
                .setEndTime(LocalDateTime.parse(endTime, formatter));
        int insert = questionnaireMapper.insert(questionnaire);

        if (insert != 1) {
            throw new CheckedException("插入数据库失败");
        }
        return id;
    }
}
