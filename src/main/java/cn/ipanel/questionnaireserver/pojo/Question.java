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
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(value = "cn.ipanel.questionnaireserver.pojo.Question")
@Data
@TableName(value = "question")
@Accessors(chain = true)
public class Question implements Serializable {
    /**
     * 题目id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "题目id")
    private Long id;

    /**
     * 题目类型{1：单选，2：多选，3：文本，4：附件上传}
     */
    @TableField(value = "question_type")
    @ApiModelProperty(value = "题目类型{1：单选，2：多选，3：文本，4：附件上传}")
    @NotNull(message = "题目类型不能为null")
    @Range(min = 1, max = 4, message = "题目类型不正确")
    private Integer questionType;

    /**
     * 是否必填：{0:可选，1：必填}
     */
    @TableField(value = "required")
    @ApiModelProperty(value = "是否必填：{0:可选，1：必填}")
    @NotNull(message = "required不能为空")
    @Range(min = 0, max = 1, message = "参数错误")
    private Integer required;

    /**
     * 题目标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value = "题目标题")
    @NotEmpty(message = "题目标题不能为空")
    private String title;

    /**
     * 题目题干
     */
    @TableField(value = "body")
    @ApiModelProperty(value = "题目题干")
    private String body;

    /**
     * 是否作为题库，1：不作为，2放入题库
     */
    @TableField(value = "question_bank")
    @ApiModelProperty(value = "是否作为题库，1：不作为，2放入题库")
    @NotNull(message = "questionBank不能为空")
    @Range(min = 0, max = 1, message = "参数错误")
    private Integer questionBank;

    private static final long serialVersionUID = 1L;

    public static final String COL_QUESTION_TYPE = "question_type";

    public static final String COL_REQUIRED = "required";

    public static final String COL_TITLE = "title";

    public static final String COL_BODY = "body";

    public static final String COL_QUESTION_BANK = "question_bank";
}