package xyz.guqing.app.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统配置类
 * @author guqing
 * @date 2019-12-25 13:28
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.config")
public class AppProperties {
    private boolean isDocDisabled;
}
