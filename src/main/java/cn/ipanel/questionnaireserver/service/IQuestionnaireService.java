package cn.ipanel.questionnaireserver.service;

import cn.ipanel.questionnaireserver.pojo.Question;
import cn.ipanel.questionnaireserver.vo.R;

import java.util.List;

public interface IQuestionnaireService {


    R addQuestionnaire(String title, String description, String startTime, String endTime);

    R addQuestionnaire(String title, String description, List<Question> questionList,String startTime, String endTime);

    R releaseQuestionnaire(Integer QuestionnaireId);
}
