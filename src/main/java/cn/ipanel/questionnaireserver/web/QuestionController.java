package cn.ipanel.questionnaireserver.web;

import cn.ipanel.questionnaireserver.pojo.Question;
import cn.ipanel.questionnaireserver.service.IQuestionService;
import cn.ipanel.questionnaireserver.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    IQuestionService questionService;

    @RequestMapping("/addQuestionAsBank")
    public R addQuestionAsBank(Question question) {
        log.info("addQuestionAsBank params: question={}", question.toString());
        return questionService.addQuestionAsBank(question);
    }
}
