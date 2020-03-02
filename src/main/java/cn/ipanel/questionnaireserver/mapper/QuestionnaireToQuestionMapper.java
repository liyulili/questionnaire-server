package cn.ipanel.questionnaireserver.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.ipanel.questionnaireserver.pojo.QuestionnaireToQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import javax.validation.constraints.NotNull;

/**
 * @author lenovo
 */
@Mapper
public interface QuestionnaireToQuestionMapper extends BaseMapper<QuestionnaireToQuestion> {

    /**
     * 根据问卷id查询统计数据
     *
     * @param questionnaireId 问卷id
     * @return
     */
    List<String> selectStatisticsByQuestionnaireId(@Param("questionnaireId") Long questionnaireId);

    /**
     * 根据问卷id查询实体类
     *
     * @param questionnaireId 问卷id
     * @return QuestionnaireToQuestion集合
     */
    List<QuestionnaireToQuestion> selectAllByQuestionnaireId(@Param("questionnaireId") Long questionnaireId);

    /**
     * 根据问卷id查询对应的问题id
     *
     * @param questionnaireId
     * @return
     */
    List<Long> queryQuestionIdByQuestionnaireId(@Param("questionnaireId") Long questionnaireId);

    /**
     * 根据问卷id删除问卷和问题关联表
     *
     * @param questionnaireId
     * @return
     */
    int deleteByQuestionnaireId(@Param("questionnaireId") @NotNull Long questionnaireId);


}