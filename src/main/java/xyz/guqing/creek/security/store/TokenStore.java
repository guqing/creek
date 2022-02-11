package xyz.guqing.creek.security.store;

import java.util.Collection;

/**
 * Persistence interface for OAuth2 tokens.
 *
 * @author guqing
 * @since 2022-01-23
 */
public interface TokenStore {
    /**
     * Read the authentication stored under the specified token value.
     *
     * @param token The token value under which the authentication is stored.
     * @return The authentication, or null if none.
     */
    OAuth2Authentication readAuthentication(OAuth2AccessToken token);

    /**
     * Read the authentication stored under the specified token value.
     *
     * @param token The token value under which the authentication is stored.
     * @return The authentication, or null if none.
     */
    OAuth2Authentication readAuthentication(String token);

    /**
     * Store an access token.
     *
     * @param token The token to store.
     * @param authentication the authentication key for the access token
     */
    void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication);

    /**
     * Read an access token from the store.
     *
     * @param tokenValue The token value.
     * @return The access token to read.
     */
    OAuth2AccessToken readAccessToken(String tokenValue);

    /**
     * Remove an access token from the store.
     *
     * @param token The token to remove from the store.
     */
    void removeAccessToken(OAuth2AccessToken token);

    /**
     * Store the specified refresh token in the store.
     *
     * @param refreshToken The refresh token to store.
     * @param authentication
     */
    void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication);

    /**
     * Read a refresh token from the store.
     *
     * @param tokenValue The value of the token to read.
     * @return The token.
     */
    OAuth2RefreshToken readRefreshToken(String tokenValue);

    /**
     * @param token a refresh token
     * @return the authentication originally used to grant the refresh token
     */
    OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token);

    /**
     * Remove a refresh token from the store.
     *
     * @param token The token to remove from the store.
     */
    void removeRefreshToken(OAuth2RefreshToken token);

    /**
     * Remove an access token using a refresh token. This functionality is necessary so refresh tokens can't be used to
     * create an unlimited number of access tokens.
     *
     * @param refreshToken The refresh token.
     */
    void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken);

    /**
     * Retrieve an access token stored against the provided authentication key, if it exists.
     *
     * @param authentication the authentication key for the access token
     *
     * @return the access token or null if there was none
     */
    OAuth2AccessToken getAccessToken(OAuth2Authentication authentication);

    /**
     * @param username the username to search
     * @return a collection of access tokens
     */
    Collection<OAuth2AccessToken> findTokensByUserName(String username);
}
