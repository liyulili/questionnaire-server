package cn.ipanel.questionnaireserver.service.imp;

import cn.ipanel.questionnaireserver.constant.QuestionnaireConstant;
import cn.ipanel.questionnaireserver.exception.CheckedException;
import cn.ipanel.questionnaireserver.mapper.QuestionMapper;
import cn.ipanel.questionnaireserver.mapper.QuestionnaireMapper;
import cn.ipanel.questionnaireserver.mapper.QuestionnaireToQuestionMapper;
import cn.ipanel.questionnaireserver.mapper.RecordMapper;
import cn.ipanel.questionnaireserver.pojo.Question;
import cn.ipanel.questionnaireserver.pojo.Questionnaire;
import cn.ipanel.questionnaireserver.pojo.QuestionnaireToQuestion;
import cn.ipanel.questionnaireserver.pojo.Record;
import cn.ipanel.questionnaireserver.service.IQuestionnaireService;
import cn.ipanel.questionnaireserver.constant.QuestionConstant;
import cn.ipanel.questionnaireserver.util.IdWorker;
import cn.ipanel.questionnaireserver.vo.QuestionBody;
import cn.ipanel.questionnaireserver.vo.QuestionStatistics;
import cn.ipanel.questionnaireserver.vo.QuestionnaireResult;
import cn.ipanel.questionnaireserver.vo.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lenovo
 */
@Slf4j
@Service
public class QuestionnaireServiceImpl implements IQuestionnaireService {

    @Autowired
    QuestionnaireMapper questionnaireMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    QuestionnaireToQuestionMapper questionnaireToQuestionMapper;

    @Autowired
    IdWorker idWorker;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");


    @Transactional(rollbackFor = Exception.class)
    @Override
    public R addQuestionnaire(String title, String description, LocalDateTime startTime, LocalDateTime endTime) {

        long id = addQuestionnaireToDataBase(title, description, startTime, endTime);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        return R.ok(jsonObject);
    }

    @Transactional
    @Override
    public R addQuestionnaire(String title, String description, List<Question> questionList, LocalDateTime startTime, LocalDateTime endTime) {

        //添加问卷到数据库返回问卷id
        long id = addQuestionnaireToDataBase(title, description, startTime, endTime);

        //解析Question
        questionList.forEach(question -> {
            int insert = questionMapper.insert(question);
            QuestionnaireToQuestion questionnaireToQuestion = new QuestionnaireToQuestion().setQuestionnaireId(id).setQuestionId(question.getId());
            int insert1 = questionnaireToQuestionMapper.insert(questionnaireToQuestion);
            if (insert != 1) {
                throw new CheckedException("插入数据库失败");
            }
        });
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R releaseQuestionnaire(Long questionnaireId) {
        int update = questionnaireMapper.update(null, new UpdateWrapper<Questionnaire>().lambda()
                .eq(Questionnaire::getId,questionnaireId)
                .set(Questionnaire::getStatus, QuestionnaireConstant.QUESTIONNAIRE_STATUS_RELEASE));
        log.info("releaseQuestionnaire questionnaireId={}，update={}",questionnaireId,update);
        return R.ok();
    }

    @Transactional
    @Override
    public R deleteQuestionnaire(Long questionnaireId) {

        //1.先删除问卷
        int deleteQuestionnaire = questionnaireMapper.deleteById(questionnaireId);

        //2,3可以考虑异步编程

        //2.删除问题和统计
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(1);
        map.put(QuestionnaireToQuestion.COL_QUESTIONNAIREID, questionnaireId);
        int deleteByMap = questionnaireToQuestionMapper.deleteByMap(map);
        //TODO 删除非题库标记的问题

        //3.删除答题记录
        map.clear();
        map.put(Record.COL_QUESTIONNAIRE_ID, questionnaireId);
        int i1 = recordMapper.deleteByMap(map);
        return R.ok();
    }

    @Override
    public R queryQuestionnaireList(LocalDateTime startTime, LocalDateTime endTime, Integer status, Integer sortType) {

        List<Questionnaire> questionnaireList = questionnaireMapper.selectList(
                new QueryWrapper<Questionnaire>()
                        .lambda()
                        .eq(status != null, Questionnaire::getStatus, status)
                        .ge(startTime != null, Questionnaire::getStartTime, startTime)
                        .le(endTime != null, Questionnaire::getEndTime, endTime)
                        .orderByAsc(sortType == 1, Questionnaire::getCreateTime)
                        .orderByDesc(sortType == 2, Questionnaire::getCreateTime));

        return R.ok(questionnaireList);
    }

    @Override
    public R queryQuestionListByQuestionnaireId(Long questionnaireId) {

        List<Question> questionList = questionMapper.selectList(new QueryWrapper<Question>().lambda()
                .inSql(Question::getId, "select question_id from questionnaire_to_question where questionnaire_id =" + questionnaireId));

        return R.ok(questionList);
    }

    @Transactional
    @Override
    public R addQuestionToQuestionnaire(Question question, Long questionnaireId, Integer order) {
        question.setId(null);
        questionMapper.insert(question);
        QuestionnaireToQuestion questionnaireToQuestion = new QuestionnaireToQuestion()
                .setQuestionId(question.getId())
                .setQuestionnaireId(questionnaireId)
                .setOrder(order);
        questionnaireToQuestionMapper.insert(questionnaireToQuestion);
        return R.ok();
    }

    @Transactional
    @Override
    public R addQuestionToQuestionnaire(Long[] questionIds, Long questionnaireId) {

        for (int i = 0; i < questionIds.length; i++) {
            questionnaireToQuestionMapper.insert(new QuestionnaireToQuestion()
                    .setQuestionId(questionIds[i])
                    .setQuestionnaireId(questionnaireId));
        }

        return R.ok();
    }

    @Override
    public R submitQuestionnaire(Long questionnaireId, Map<Long, String> answers) {

        //储存记录
        List recordList = new ArrayList();
        answers.forEach((id, answer) -> recordList.add(new Record()
                .setQuestionnaireId(questionnaireId)
                .setQuestionId(id)
                .setAnswer(answer))

        );
        int i = recordMapper.insertList(recordList);

        //统计数据，选择题统计各项票数，非选择题统计有效数
        List<QuestionnaireToQuestion> questionnaireToQuestionList = questionnaireToQuestionMapper.selectAllByQuestionnaireId(questionnaireId);
        questionnaireToQuestionList.forEach(questionnaireToQuestion -> {

            Question question = questionMapper.selectById(questionnaireToQuestion.getId());
            //提交的答案存在且不为空
            if (answers.get(question.getId()) != null && StringUtils.isNotBlank(answers.get(question.getId()))) {
                //没有记录时
                if (StringUtils.isBlank(questionnaireToQuestion.getStatistics())) {

                    prepareStatistics(questionnaireToQuestion, question);
                }

                countAnswerStatistics(answers, questionnaireToQuestion, question);
                //将数据更新到数据库
                int updateById = questionnaireToQuestionMapper.updateById(questionnaireToQuestion);
            }
        });
        return R.ok();
    }

    @Override
    public R queryQuestionnaireResult(Long questionnaireId) {


        Questionnaire questionnaire = questionnaireMapper.selectById(questionnaireId);

        List<QuestionnaireToQuestion> questionnaireToQuestionList = questionnaireToQuestionMapper.selectAllByQuestionnaireId(questionnaireId);
        HashMap<Long, String> map = Maps.newHashMapWithExpectedSize(questionnaireToQuestionList.size());
        questionnaireToQuestionList.forEach(questionnaireToQuestion -> map.put(questionnaireToQuestion.getQuestionId(), questionnaireToQuestion.getStatistics()));

        List<Long> questionIdList = questionnaireToQuestionList.stream().map(QuestionnaireToQuestion::getQuestionId).collect(Collectors.toList());
        List<Question> questionList = questionMapper.selectBatchIds(questionIdList)
                .stream()
                .map(question -> {
                    if (map.get(question.getId()) != null && StringUtils.isNotBlank(map.get(question.getId()))) {
                        question.setBody(map.get(question.getId()));
                    }
                    return question;
                })
                .collect(Collectors.toList());
        QuestionnaireResult questionnaireResult = new QuestionnaireResult().setQuestionnaire(questionnaire).setQuestionList(questionList);

        return R.ok(questionnaireResult);
    }

    /**
     * 对初始化好的statistics字段进行统计
     *
     * @param answers                 提交的答案
     * @param questionnaireToQuestion 统计记录实体类
     * @param question                问题实体类
     */
    private void countAnswerStatistics(Map<Long, String> answers, QuestionnaireToQuestion questionnaireToQuestion, Question question) {
        if (question.getQuestionType().equals(QuestionConstant.QUESTION_TYPE_SINGLE_CHOICE)
                || question.getQuestionType().equals(QuestionConstant.QUESTION_TYPE_MULTIPLE_CHOICE)) {

            //选择题
            List<QuestionStatistics> statisticsList = JSON.parseArray(questionnaireToQuestion.getStatistics(), QuestionStatistics.class);
            statisticsList.forEach(statistics -> {
                String[] split = answers.get(question.getId()).trim().split("|");
                for (String s : split) {
                    if (statistics.getCode().equals(s)) {
                        statistics.setNum(statistics.getNum() + 1);
                    }
                }
            });

        } else {
            //为其他类型题目是暂时考虑有效作答数，只储存数量
            int num = Integer.parseInt(questionnaireToQuestion.getStatistics());
            questionnaireToQuestion.setStatistics(String.valueOf(num + 1));
        }
    }

    /**
     * 对空白统计进行预填充
     *
     * @param questionnaireToQuestion 待统计记录实体类
     * @param question                问题实体类
     */
    private void prepareStatistics(QuestionnaireToQuestion questionnaireToQuestion, Question question) {
        if (question.getQuestionType().equals(QuestionConstant.QUESTION_TYPE_SINGLE_CHOICE)
                || question.getQuestionType().equals(QuestionConstant.QUESTION_TYPE_MULTIPLE_CHOICE)) {
            List<QuestionStatistics> questionStatisticsList = JSON.parseArray(question.getBody(), QuestionBody.class)
                    .stream()
                    .map(questionBody -> new QuestionStatistics()
                            .setNum(0)
                            .setCode(questionBody.getCode())
                            .setContent(questionBody.getContent())
                            .setDesc(questionBody.getDesc())
                            .setPic(questionBody.getPic()))
                    .collect(Collectors.toList());
            questionnaireToQuestion.setStatistics(JSON.toJSONString(questionStatisticsList));
        } else {
            questionnaireToQuestion.setStatistics(String.valueOf(0));
        }
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
    private long addQuestionnaireToDataBase(String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        long id = idWorker.nextId();
        System.out.println("id = " + id);
        Questionnaire questionnaire = new Questionnaire()
                .setId(id)
                .setTitle(title)
                .setDescription(description)
                .setStatus(QuestionnaireConstant.QUESTIONNAIRE_STATUS_UN_RELEASE)
                .setCreateTime(LocalDateTime.now())
                .setStartTime(startTime)
                .setEndTime(endTime);
        int insert = questionnaireMapper.insert(questionnaire);

        if (insert != 1) {
            throw new CheckedException("插入数据库失败");
        }
        return id;
    }
}
