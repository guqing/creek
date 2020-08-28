package xyz.guqing.app.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author guqing
 * @date 2019-12-22 24:53
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth.security.token")
public class TokenProperties {
    /**
     * 30分钟(以秒s计)
     */
    private long expirationTime = 7200;
    /**
     * 允许过期时间时钟偏移秒s为单位
     */
    private long allowedClockSkewSeconds = 1800;
    /**
     *  JWT密码
     */
    private String secret = "HappyLandSecret123456";
    /**
     * Token前缀
     */
    private String tokenPrefix = "bearer";
    /**
     * 存放Token的Header Key
     */
    private String headerString = "Authorization";
}
