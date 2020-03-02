package cn.ipanel.questionnaireserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author liyu
 * @date 2020/3/2 9:00
 * @description
 */
@Configuration
public class LocalDateTimeConfig {
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        // 使用 lambda 表达式有问题，暂未解决
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String s) {
                // 时间格式化
                // 创建格式化/解析模板
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // 解析
                LocalDateTime parse = LocalDateTime.parse(s, dateTimeFormatter);
                return parse;
            }
        };
    }

    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String s) {
                // 时间格式化
                // 创建格式化/解析模板
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                // 解析
                LocalDate parse = LocalDate.parse(s, dateTimeFormatter);
                return parse;
            }
        };
    }

    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String s) {
                // 时间格式化
                // 创建格式化/解析模板
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                // 解析
                LocalTime parse = LocalTime.parse(s, dateTimeFormatter);
                return parse;
            }
        };
    }
}
