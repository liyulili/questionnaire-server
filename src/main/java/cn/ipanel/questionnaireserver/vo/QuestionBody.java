package cn.ipanel.questionnaireserver.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liyu
 * @date 2020/1/20 10:04
 * @description question实体类中body映射
 */
@Data
public class QuestionBody {
    public String code;
    public String content;
    public String desc;
    public String pic;
}
