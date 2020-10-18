package xyz.guqing.creek.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guqing
 * @date 2020-04-21 10:02
 */
@Data
@ConfigurationProperties(prefix = "creek.thread.pool")
public class ThreadPoolProperties {
    private Integer corePoolSize = 8;
    private Integer maximumPoolSize = 100;
    private Integer queueSize = 100000;
    private Integer keepAliveTime = 10;
}
