package cn.ipanel.questionnaireserver.web;

import cn.ipanel.questionnaireserver.pojo.Questionnaire;
import cn.ipanel.questionnaireserver.service.IQuestionnaireService;
import cn.ipanel.questionnaireserver.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired
    IQuestionnaireService questionnaireService;


    public R addQuestionnaire(String title, String description, String startTime, String endTime) {

        log.info("addQuestionnaire params: title={},description={},startTime={},endTime={}", title, description, startTime, endTime);
        return questionnaireService.addQuestionnaire(title, description, startTime, endTime);
    }

    public R releaseQuestionnaire(Long questionnaireId) {
        log.info("releaseQuestionnaire params: questionnaireId={}", questionnaireId);
        return questionnaireService.releaseQuestionnaire(questionnaireId);
    }
}
