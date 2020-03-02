package cn.ipanel.questionnaireserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lenovo
 */
@ApiModel(value = "cn.ipanel.questionnaireserver.pojo.QuestionnaireToQuestion")
@Data
@TableName(value = "questionnaire_to_question")
@Accessors(chain = true)
public class QuestionnaireToQuestion implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "null")
    private Long id;

    @TableField(value = "questionnaire_id")
    @ApiModelProperty(value = "null")
    private Long questionnaireId;

    @TableField(value = "question_id")
    @ApiModelProperty(value = "null")
    private Long questionId;

    @TableField(value = "statistics")
    @ApiModelProperty(value = "null")
    private String statistics;

    @TableField(value = "od")
    @ApiModelProperty(value = "null")
    private Integer od;

    private static final long serialVersionUID = 1L;

    public static final String COL_QUESTIONNAIREID = "questionnaire_id";

    public static final String COL_QUESTIONID = "question_id";

    public static final String COL_STATISTICS = "statistics";

    public static final String COL_OD = "od";
}