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
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lenovo
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired
    IQuestionnaireService questionnaireService;

    @Autowired
    IQuestionService questionService;


    @PostMapping("/addQuestionnaire")
    public R addQuestionnaire(@RequestParam @NotEmpty(message = "标题不能为空") String title,
                              @RequestParam(required = false) String description,
                              @RequestParam LocalDateTime startTime,
                              @RequestParam LocalDateTime endTime) {

        log.info("addQuestionnaire params: title={},description={},startTime={},endTime={}", title, description, startTime, endTime);
        return questionnaireService.addQuestionnaire(title, description, startTime, endTime);
    }

    @PostMapping("/addQuestionnaireWithQuestion")
    public R addQuestionnaire(@RequestParam @NotEmpty(message = "标题不能为空") String title,
                              @RequestParam(required = false) String description,
                              @RequestParam LocalDateTime startTime,
                              @RequestParam LocalDateTime endTime,
                              @RequestParam List<Question> questionList) {

        log.info("addQuestionnaireWithQuestion params: title={},description={},startTime={},endTime={},questionList.size={}", title, description, startTime, endTime, questionList.size());
        return questionnaireService.addQuestionnaire(title, description, questionList, startTime, endTime);
    }


    @PostMapping("/releaseQuestionnaire")
    public R releaseQuestionnaire(@RequestParam @NotNull(message = "问卷id不能为空") Long questionnaireId) {
        log.info("releaseQuestionnaire params: questionnaireId={}", questionnaireId);
        return questionnaireService.releaseQuestionnaire(questionnaireId);
    }

    @DeleteMapping("/deleteQuestionnaire")
    public R deleteQuestionnaire(@RequestParam @NotNull(message = "问卷id不能为空") Long questionnaireId) {
        log.info("deleteQuestionnaire params: questionnaireId={}", questionnaireId);
        return questionnaireService.deleteQuestionnaire(questionnaireId);
    }

    @GetMapping("/queryQuestionnaire")
    public R queryQuestionnaire(@RequestParam(required = false) LocalDateTime startTime,
                                @RequestParam(required = false) LocalDateTime endTime,
                                @RequestParam(required = false) Integer status,
                                @RequestParam(required = false) Integer sortType) {
        log.info("queryQuestionnaireList params: startTime={},endTime={},status={},sortType={}", startTime, endTime, status, sortType);
        return questionnaireService.queryQuestionnaireList(startTime, endTime, status, sortType);
    }

    @GetMapping("/queryQuestionList")
    public R queryQuestionList(@RequestParam @NotNull(message = "问卷id不能为空") Long questionnaireId) {
        log.info("queryQuestionList params: questionnaireId={}", questionnaireId);
        return questionnaireService.queryQuestionListByQuestionnaireId(questionnaireId);
    }

    @PostMapping("/addQuestionToQuestionnaire")
    public R addQuestionToQuestionnaire( @Validated Question question,
                                        @RequestParam @NotNull(message = "问卷id不能为空") Long questionnaireId,
                                        @RequestParam(required = false) Integer order) {
        log.info("addQuestionToQuestionnaire params: question={},questionnaireId={},order={}", question.toString(), questionnaireId, order);
        return questionnaireService.addQuestionToQuestionnaire(question, questionnaireId, order);

    }

    @PostMapping("/addQuestionFromBank")
    public R addQuestionToQuestionnaire(@RequestParam @Size(min = 1, message = "问题不能为空") Long[] questionIds,
                                        @RequestParam @NotNull(message = "问卷id不能为空") Long questionnaireId) {
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
    @PostMapping("/submitQuestionnaire")
    public R submitQuestionnaire(@RequestParam @NotNull(message = "问卷id不能为空") Long questionnaireId,
                                 @RequestParam @NotEmpty(message = "答案不能为空") String answers) {
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


    @GetMapping("/queryQuestionnaireResult")
    public R queryQuestionnaireResult(@RequestParam @NotNull(message = "问卷id不能为空") Long questionnaireId) {
        log.info("queryQuestionnaireResult params: questionnaireId={}", questionnaireId);
        return questionnaireService.queryQuestionnaireResult(questionnaireId);
    }
}
