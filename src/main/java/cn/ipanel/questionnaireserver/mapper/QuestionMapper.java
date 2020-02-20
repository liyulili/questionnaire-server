package cn.ipanel.questionnaireserver.mapper;

import cn.ipanel.questionnaireserver.pojo.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}