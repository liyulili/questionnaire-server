package cn.ipanel.questionnaireserver.mapper;

import cn.ipanel.questionnaireserver.pojo.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lenovo
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    /**
     * 批量插入答题记录
     *
     * @param list 答题记录集合
     * @return
     */
    int insertList(@Param("list") List<Record> list);

    /**
     * 根据问卷id删除记录
     * @param questionnaireId 问卷id
     * @return
     */
    int deleteByQuestionnaireId(@Param("questionnaireId")Long questionnaireId);


}