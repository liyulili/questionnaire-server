package cn.ipanel.questionnaireserver.web;

import cn.ipanel.questionnaireserver.pojo.Questionnaire;
import cn.ipanel.questionnaireserver.vo.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    public R addQuestionnaire(String title, String description, LocalDateTime startTime, LocalDateTime endTime){
        Questionnaire questionnaire = new Questionnaire();


        return R.ok();
    }
}
