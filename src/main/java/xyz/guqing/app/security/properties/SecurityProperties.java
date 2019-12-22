package xyz.guqing.app.security.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("app")
public class SecurityProperties {
    private final TokenProperties tokenProperties;
    private final LoginProperties loginProperties;

    @Autowired
    public SecurityProperties(LoginProperties loginProperties,
                         TokenProperties tokenProperties) {
        this.loginProperties = loginProperties;
        this.tokenProperties = tokenProperties;
    }
}
