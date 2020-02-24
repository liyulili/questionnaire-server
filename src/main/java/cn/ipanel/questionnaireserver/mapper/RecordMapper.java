package cn.ipanel.questionnaireserver.mapper;

import java.util.Collection;

import cn.ipanel.questionnaireserver.pojo.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    int deleteByQuestionnaireIdIn(@Param("questionnaireIdCollection") Collection<Long> questionnaireIdCollection);


}