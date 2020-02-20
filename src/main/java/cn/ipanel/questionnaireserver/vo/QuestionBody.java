package cn.ipanel.questionnaireserver.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liyu
 * @date 2020/1/20 10:04
 * @description
 */
@Data
public class QuestionBody {
    private String title;
    private List<QuestionBodyCell> body;
    private String standardAnswer;
}
