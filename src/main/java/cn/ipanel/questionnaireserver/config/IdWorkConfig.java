package cn.ipanel.questionnaireserver.config;

import cn.ipanel.questionnaireserver.util.IdWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author liyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "idworker")
public class IdWorkConfig {
    /**
     * 工作机器ID
     */
    private Long workerId;
    /**
     * 序列号
     */
    private Long dataCenterId;

    @Bean
    public IdWorker creteIdWorker() {
        return new IdWorker(workerId, dataCenterId);
    }
}
