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
    OAuthAccessToken readAuthentication(OAuthAccessToken token);

    /**
     * Read the authentication stored under the specified token value.
     *
     * @param token The token value under which the authentication is stored.
     * @return The authentication, or null if none.
     */
    OAuthAccessToken readAuthentication(String token);

    /**
     * Store an access token.
     *
     * @param token The token to store.
     */
    void storeAccessToken(OAuthAccessToken token);

    /**
     * Read an access token from the store.
     *
     * @param tokenValue The token value.
     * @return The access token to read.
     */
    OAuthAccessToken readAccessToken(String tokenValue);

    /**
     * Remove an access token from the store.
     *
     * @param token The token to remove from the store.
     */
    void removeAccessToken(OAuthAccessToken token);

    /**
     * Store the specified refresh token in the store.
     *
     * @param refreshToken The refresh token to store.
     */
    void storeRefreshToken(OauthRefreshToken refreshToken);

    /**
     * Read a refresh token from the store.
     *
     * @param tokenValue The value of the token to read.
     * @return The token.
     */
    OauthRefreshToken readRefreshToken(String tokenValue);

    /**
     * @param token a refresh token
     * @return the authentication originally used to grant the refresh token
     */
    OAuthAccessToken readAuthenticationForRefreshToken(OauthRefreshToken token);

    /**
     * Remove a refresh token from the store.
     *
     * @param token The token to remove from the store.
     */
    void removeRefreshToken(OauthRefreshToken token);

    /**
     * Remove an access token using a refresh token. This functionality is necessary so refresh tokens can't be used to
     * create an unlimited number of access tokens.
     *
     * @param refreshToken The refresh token.
     */
    void removeAccessTokenUsingRefreshToken(OauthRefreshToken refreshToken);

    /**
     * Retrieve an access token stored against the provided authentication key, if it exists.
     *
     * @param authentication the authentication key for the access token
     *
     * @return the access token or null if there was none
     */
    OAuthAccessToken getAccessToken(OAuthAccessToken authentication);

    /**
     * @param username the username to search
     * @return a collection of access tokens
     */
    Collection<OAuthAccessToken> findTokensByUsername(String username);
}
