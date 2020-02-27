package cn.ipanel.questionnaireserver.vo;

import cn.ipanel.questionnaireserver.pojo.Question;
import cn.ipanel.questionnaireserver.pojo.Questionnaire;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class QuestionnaireResult {
    private Questionnaire questionnaire;
    private List<Question> questionList;
}
