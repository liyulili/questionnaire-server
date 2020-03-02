package cn.ipanel.questionnaireserver.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import cn.ipanel.questionnaireserver.pojo.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lenovo
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 删除问卷
     * @param idCollection 题目id集合
     * @param questionBank 是否为题库
     * @return
     */
    int deleteByIdInAndQuestionBank(@Param("idCollection")Collection<Long> idCollection,@Param("questionBank")Integer questionBank);

}