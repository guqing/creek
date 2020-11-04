package xyz.guqing.creek.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author guqing
 * @date 2019-12-22 24:53
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth.security.login")
public class LoginProperties {
    private String loginUrl = "/authorize/token";
    private String logoutUrl = "/authorize/logout";
    /**
     * 社交登陆成功后跳转地址
     */
    private String socialRedirectUrl;
}
