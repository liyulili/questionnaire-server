package cn.ipanel.questionnaireserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liyu
 * @date 2020/1/14 10:01
 * @description Swagger2配置类
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("cn.ipanel.questionnaireapp.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("问卷App")
                .description("问卷App端 API 1.0 操作文档")
                //服务条款网址
//                .termsOfServiceUrl("http://www.baidu.com/")
                .version("1.0")
                .contact(new Contact("liyu", "http://www.baidu.com/", "1170667320@qq.com.com"))
                .build();
    }
}
