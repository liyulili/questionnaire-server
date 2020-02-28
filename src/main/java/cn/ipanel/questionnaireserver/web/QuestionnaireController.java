package cn.ipanel.questionnaireserver.web;

import cn.ipanel.questionnaireserver.pojo.Question;
import cn.ipanel.questionnaireserver.service.IQuestionService;
import cn.ipanel.questionnaireserver.service.IQuestionnaireService;
import cn.ipanel.questionnaireserver.vo.QuestionAnswer;
import cn.ipanel.questionnaireserver.vo.R;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Validated
@Slf4j
@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired
    IQuestionnaireService questionnaireService;

    @Autowired
    IQuestionService questionService;


    @RequestMapping("/addQuestionnaire")
    public R addQuestionnaire(@RequestParam @NotEmpty(message = "标题不能为空") String title,
                              String description,
                              String startTime,
                              String endTime) {

        log.info("addQuestionnaire params: title={},description={},startTime={},endTime={}", title, description, startTime, endTime);
        return questionnaireService.addQuestionnaire(title, description, startTime, endTime);
    }

    @RequestMapping("/addQuestionnaireWithQuestion")
    public R addQuestionnaire(String title, String description, String startTime, String endTime, List<Question> questionList) {

        log.info("addQuestionnaireWithQuestion params: title={},description={},startTime={},endTime={},questionList.size={}", title, description, startTime, endTime, questionList.size());
        return questionnaireService.addQuestionnaire(title, description, questionList, startTime, endTime);
    }


    @RequestMapping("/releaseQuestionnaire")
    public R releaseQuestionnaire(Long questionnaireId) {
        log.info("releaseQuestionnaire params: questionnaireId={}", questionnaireId);
        return questionnaireService.releaseQuestionnaire(questionnaireId);
    }

    @RequestMapping("/deleteQuestionnaire")
    public R deleteQuestionnaire(Long questionnaireId) {
        log.info("deleteQuestionnaire params: questionnaireId={}", questionnaireId);
        return questionnaireService.deleteQuestionnaire(questionnaireId);
    }

    @RequestMapping("/queryQuestionnaire")
    public R queryQuestionnaire(String startTime, String endTime, Integer status, Integer sortType) {
        log.info("queryQuestionnaireList params: startTime={},endTime={},status={},sortType={}", startTime, endTime, status, sortType);
        return questionnaireService.queryQuestionnaireList(startTime, endTime, status, sortType);
    }

    @RequestMapping("/queryQuestionList")
    public R queryQuestionList(Long questionnaireId) {
        log.info("queryQuestionList params: questionnaireId={}", questionnaireId);
        return questionnaireService.queryQuestionListByquestionnaireId(questionnaireId);
    }

    @RequestMapping("/addQuestionToQuestionnaire")
    public R addQuestionToQuestionnaire(Question question, Long questionnaireId, Integer order) {
        log.info("addQuestionToQuestionnaire params: question={},questionnaireId={},order={}", question.toString(), questionnaireId, order);
        return questionnaireService.addQuestionToQuestionnaire(question, questionnaireId, order);

    }

    @RequestMapping("/addQuestionFromBank")
    public R addQuestionToQuestionnaire(Long[] questionIds, Long questionnaireId) {
        log.info("addQuestionFromBank params: questionIds={},questionnaireId={}", Arrays.toString(questionIds), questionnaireId);
        return questionnaireService.addQuestionToQuestionnaire(questionIds, questionnaireId);
    }

    /**
     * 提交问卷
     *
     * @param questionnaireId 问卷id
     * @param answers         答案 [{"questionId":1,"answer":"A|D"},{"questionId":2,"answer":"XXXX"}]
     * @return
     */
    @RequestMapping("/submitQuestionnaire")
    public R submitQuestionnaire(Long questionnaireId, String answers) {
        log.info("submitQuestionnaire params: questionnaireId={},answers={}", questionnaireId, answers);
        if (StringUtils.isBlank(answers)) {
            return R.error("请填写有效答案");
        }
        //将JSON字符串形式的answers转换为map
        List<QuestionAnswer> questionAnswers = JSON.parseArray(answers, QuestionAnswer.class);
        Map<Long, String> answersMap = Maps.newHashMapWithExpectedSize(questionAnswers.size());
        questionAnswers.forEach(questionAnswer -> answersMap.put(questionAnswer.getQuestionId(), questionAnswer.getAnswer()));

        return questionnaireService.submitQuestionnaire(questionnaireId, answersMap);
    }


    @RequestMapping("/queryQuestionnaireResult")
    public R queryQuestionnaireResult(Long questionnaireId) {
        log.info("queryQuestionnaireResult params: questionnaireId={}", questionnaireId);
        return questionnaireService.queryQuestionnaireResult(questionnaireId);
    }
}
