package cn.ipanel.questionnaireserver.config;

import cn.ipanel.questionnaireserver.exception.CheckedException;
import cn.ipanel.questionnaireserver.pojo.Question;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liyu
 * @date 2020/3/2 11:25
 * @description
 */
@Configuration
public class StringToDataConverter {
    @Bean
    public Converter<String, List<Question>> StringToQuestionListConverter() {
        return new Converter<String, List<Question>>() {
            @Override
            public List<Question> convert(String s) {
                List<Question> questionList = null;
                if (StringUtils.isBlank(s)) {
                    return null;
                }
                try {
                    questionList = JSON.parseArray(s, Question.class);
                } catch (Exception e) {
                    throw new CheckedException("Json转换失败");
                }
                return questionList;
            }
        };
    }
}
