package cn.ipanel.questionnaireserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.ipanel.questionnaireserver.mapper")
public class QuestionnaireServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionnaireServerApplication.class, args);
    }

}
