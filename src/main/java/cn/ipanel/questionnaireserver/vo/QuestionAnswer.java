package cn.ipanel.questionnaireserver.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 解析用户提交的答案
 */

@Data
@Accessors(chain = true)
public class QuestionAnswer {
    private Long questionId;
    private String answer;
}
