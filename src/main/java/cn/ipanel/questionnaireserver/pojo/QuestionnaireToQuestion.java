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

@ApiModel(value = "cn.ipanel.questionnaireserver.pojo.QuestionnaireToQuestion")
@Data
@TableName(value = "questionnaire_to_question")
@Accessors(chain = true)
public class QuestionnaireToQuestion implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "null")
    private Long id;

    @TableField(value = "questionnaireId")
    @ApiModelProperty(value = "null")
    private Long questionnaireId;

    @TableField(value = "questionId")
    @ApiModelProperty(value = "null")
    private Long questionId;

    @TableField(value = "statistics")
    @ApiModelProperty(value = "null")
    private String statistics;

    @TableField(value = "order")
    @ApiModelProperty(value = "null")
    private Integer order;

    private static final long serialVersionUID = 1L;

    public static final String COL_QUESTIONNAIREID = "questionnaireId";

    public static final String COL_QUESTIONID = "questionId";

    public static final String COL_STATISTICS = "statistics";

    public static final String COL_ORDER = "order";
}