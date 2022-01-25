package xyz.guqing.creek.security.store;

import java.util.Date;

/**
 * @author guqing
 * @since 2022-01-25
 */
public class DefaultExpiringOAuth2RefreshToken extends DefaultOAuth2RefreshToken
    implements ExpiringOAuth2RefreshToken {

    private final Date expiration;

    /**
     * @param value
     */
    public DefaultExpiringOAuth2RefreshToken(String value, Date expiration) {
        super(value);
        this.expiration = expiration;
    }

    /**
     * The instant the token expires.
     *
     * @return The instant the token expires.
     */
    public Date getExpiration() {
        return expiration;
    }
}
