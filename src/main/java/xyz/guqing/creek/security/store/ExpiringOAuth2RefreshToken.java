package xyz.guqing.creek.security.store;

import java.util.Date;

/**
 * @author guqing
 * @since 2022-01-25
 */
public interface ExpiringOAuth2RefreshToken extends OAuth2RefreshToken {

    Date getExpiration();
}
