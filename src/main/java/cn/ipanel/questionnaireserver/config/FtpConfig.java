package cn.ipanel.questionnaireserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author liyu
 * @date 2020/3/3 11:12
 * @description
 */
@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpConfig {

    /**
     * ftp服务器地址
     */
    private String url;

    /**
     * ftp服务器端口
     */
    private Integer port;

    /**
     * ftp服务器用户名
     */
    private String username;

    /**
     * ftp服务器密码
     */
    private String password;
}
