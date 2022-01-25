package xyz.guqing.creek.security.store;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Date;

/**
 * @author guqing
 * @since 2022-01-25
 */
public interface OAuth2RefreshToken {

    /**
     * The value of the token.
     *
     * @return The value of the token.
     */
    @JsonValue
    String getValue();
}
