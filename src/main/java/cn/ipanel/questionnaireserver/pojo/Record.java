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
@ApiModel(value="cn.ipanel.questionnaireserver.pojo.Record")
@Data
@TableName(value = "record")
@Accessors(chain = true)
public class Record implements Serializable {
     @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="null")
    private Long id;

    /**
     * 问卷id
     */
    @TableField(value = "questionnaire_id")
    @ApiModelProperty(value="问卷id")
    private Long questionnaireId;

    /**
     * 问题id
     */
    @TableField(value = "question_id")
    @ApiModelProperty(value="问题id")
    private Long questionId;

    /**
     * 问卷回答结果
     */
    @TableField(value = "answer")
    @ApiModelProperty(value="问卷回答结果")
    private String answer;

    private static final long serialVersionUID = 1L;

    public static final String COL_QUESTIONNAIRE_ID = "questionnaire_id";

    public static final String COL_QUESTION_ID = "question_id";

    public static final String COL_ANSWER = "answer";
}