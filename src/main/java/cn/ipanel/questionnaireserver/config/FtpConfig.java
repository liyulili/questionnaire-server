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

    /**
     * ftp服务器存放文件的路径
     */
    private String remotePath;

    /**
     * 本地需要上传的文件的路径
     */
    private String localDir;

    /**
     * 下载文件时，存放在本地的路径
     */
    private String downDir;
}
