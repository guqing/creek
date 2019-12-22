package xyz.guqing.app.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author guqing
 * @date 2019/8/10
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth.security.login")
public class LoginProperties {
    private String loginUrl = "/auth/login";
    private String logoutUrl = "/auth/logout";
}
