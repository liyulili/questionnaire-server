package cn.ipanel.questionnaireserver.web;

import cn.ipanel.questionnaireserver.pojo.Question;
import cn.ipanel.questionnaireserver.service.IQuestionService;
import cn.ipanel.questionnaireserver.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lenovo
 */
@Slf4j
@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    IQuestionService questionService;

    @PostMapping("/addQuestionAsBank")
    public R addQuestionAsBank(@RequestBody @Validated Question question) {
        log.info("addQuestionAsBank params: question={}", question.toString());
        question.setId(null);
        return questionService.addQuestionAsBank(question);
    }
}
