package cn.ipanel.questionnaireserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(value="cn.ipanel.questionnaireserver.pojo.Questionnaire")
@Data
@Accessors(chain = true)
@TableName(value = "questionnaire")
public class Questionnaire implements Serializable {
    /**
     * 问卷id,由自增算法实现,用于标识问卷
     */
     @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="问卷id,由自增算法实现,用于标识问卷")
    private Long id;

    /**
     * 问卷标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value="问卷标题")
    private String title;

    /**
     * 问卷说明
     */
    @TableField(value = "description")
    @ApiModelProperty(value="问卷说明")
    private String description;

    /**
     * 问卷状态{1:已创建未发布 2：已发布}
     */
    @TableField(value = "status")
    @ApiModelProperty(value="问卷状态{1:已创建未发布 2：已发布}")
    private Integer status;

    @TableField(value = "createTime")
    @ApiModelProperty(value="null")
    private LocalDateTime createTime;

    @TableField(value = "startTime")
    @ApiModelProperty(value="null")
    private LocalDateTime startTime;

    @TableField(value = "endTime")
    @ApiModelProperty(value="null")
    private LocalDateTime endTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_TITLE = "title";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATETIME = "createTime";

    public static final String COL_STARTTIME = "startTime";

    public static final String COL_ENDTIME = "endTime";

}