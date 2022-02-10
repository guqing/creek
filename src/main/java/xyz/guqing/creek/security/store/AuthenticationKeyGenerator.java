package xyz.guqing.creek.security.store;

/**
 * Strategy interface for extracting a unique key from an {@link OAuth2Authentication}.
 */
public interface AuthenticationKeyGenerator {

    /**
     * @param authentication an OAuth2Authentication
     * @return a unique key identifying the authentication
     */
    String extractKey(OAuth2Authentication authentication);
}
