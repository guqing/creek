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
@ConfigurationProperties(prefix = "auth.security.token")
public class TokenProperties {
    /**
     * 30分钟(以分钟计)
     */
    private int expireAt = 30;
    /**
     * 允许过期时间时钟偏移分钟为单位
     */
    private long allowedClockSkewSeconds = 30;
    /**
     *  JWT密码
     */
    private String secret = "hello world";
    /**
     * Token前缀
     */
    private String tokenPrefix = "bearer";
    /**
     * 存放Token的Header Key
     */
    private String header = "Authorization";
}
