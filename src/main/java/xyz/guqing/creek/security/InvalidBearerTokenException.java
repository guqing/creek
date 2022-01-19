package xyz.guqing.creek.security;

import xyz.guqing.creek.security.token.OAuth2AuthenticationException;

/**
 * An {@link OAuth2AuthenticationException} that indicates an invalid bearer token.
 *
 * @author Josh Cummings
 * @author guqing
 * @see <a href="https://github.com/spring-projects/spring-security/blob/dffa0ed43001d20d3f2d596db9b38d5bc06cf74e/oauth2/oauth2-resource-server/src/main/java/org/springframework/security/oauth2/server/resource/InvalidBearerTokenException.java#L27">InvalidBearerTokenException</a>
 */
public class InvalidBearerTokenException extends OAuth2AuthenticationException {

    /**
     * Construct an instance of {@link InvalidBearerTokenException} given the provided description.
     * <p>
     * The description will be wrapped into an {@link OAuth2Error} instance as the {@code
     * error_description}.
     *
     * @param description the description
     */
    public InvalidBearerTokenException(String description) {
        super(BearerTokenErrors.invalidToken(description));
    }

    /**
     * Construct an instance of {@link InvalidBearerTokenException} given the provided description
     * and cause
     * <p>
     * The description will be wrapped into an {@link OAuth2Error} instance as the {@code
     * error_description}.
     *
     * @param description the description
     * @param cause       the causing exception
     */
    public InvalidBearerTokenException(String description, Throwable cause) {
        super(BearerTokenErrors.invalidToken(description), cause);
    }

}
