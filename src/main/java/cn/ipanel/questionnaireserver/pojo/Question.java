package cn.ipanel.questionnaireserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@ApiModel(value="cn.ipanel.questionnaireserver.pojo.Question")
@Data
@TableName(value = "question")
public class Question implements Serializable {
    /**
     * 题目id
     */
     @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="题目id")
    private Long id;

    /**
     * 问卷id
     */
    @TableField(value = "questionnaire_id")
    @ApiModelProperty(value="问卷id")
    private Long questionnaireId;

    /**
     * 题目类型{1：单选，2：多选，3：文本，4：附件上传}
     */
    @TableField(value = "question_type")
    @ApiModelProperty(value="题目类型{1：单选，2：多选，3：文本，4：附件上传}")
    private Integer questionType;

    /**
     * 是否必填：{0:可选，1：必填}
     */
    @TableField(value = "required")
    @ApiModelProperty(value="是否必填：{0:可选，1：必填}")
    private Integer required;

    /**
     * 题目标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value="题目标题")
    private String title;

    /**
     * 题目题干
     */
    @TableField(value = "body")
    @ApiModelProperty(value="题目题干")
    private String body;

    /**
     * 结果统计（只统计选择题，其他题型统计有效回答数）
     */
    @TableField(value = "statistics")
    @ApiModelProperty(value="结果统计（只统计选择题，其他题型统计有效回答数）")
    private String statistics;

    /**
     * 题目排序
     */
    @TableField(value = "order")
    @ApiModelProperty(value="题目排序")
    private Integer order;

    private static final long serialVersionUID = 1L;

    public static final String COL_QUESTIONNAIRE_ID = "questionnaire_id";

    public static final String COL_QUESTION_TYPE = "question_type";

    public static final String COL_REQUIRED = "required";

    public static final String COL_TITLE = "title";

    public static final String COL_BODY = "body";

    public static final String COL_STATISTICS = "statistics";

    public static final String COL_ORDER = "order";
}