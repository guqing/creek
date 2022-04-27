package xyz.guqing.creek.infra.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guqing
 * @date 2022-04-12
 */
@ConfigurationProperties(prefix = "creek")
public class CreekProperties {

    private final JwtProperties jwt = new JwtProperties();

    public JwtProperties getJwt() {
        return jwt;
    }
}
