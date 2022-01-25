package xyz.guqing.creek.security;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import xyz.guqing.creek.exception.AuthenticationException;
import xyz.guqing.creek.security.store.DefaultExpiringOAuth2RefreshToken;
import xyz.guqing.creek.security.store.DefaultOAuth2AccessToken;
import xyz.guqing.creek.security.store.DefaultOAuth2RefreshToken;
import xyz.guqing.creek.security.store.ExpiringOAuth2RefreshToken;
import xyz.guqing.creek.security.store.OAuth2AccessToken;
import xyz.guqing.creek.security.store.OAuth2Authentication;
import xyz.guqing.creek.security.store.OAuth2RefreshToken;
import xyz.guqing.creek.security.store.OAuth2Request;
import xyz.guqing.creek.security.store.TokenStore;

/**
 * @author guqing
 * @since 2022-01-25
 */
public class DefaultTokenServices {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(20);

    private static final Charset US_ASCII = StandardCharsets.US_ASCII;

    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 30; // default 30 days.

    private int accessTokenValiditySeconds = 60 * 60 * 12; // default 12 hours.

    private boolean supportRefreshToken = false;

    private boolean reuseRefreshToken = true;

    private TokenStore tokenStore;

    private AuthenticationManager authenticationManager;

    @Transactional
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication)
        throws AuthenticationException {

        OAuth2AccessToken existingAccessToken = tokenStore.getAccessToken(authentication);
        OAuth2RefreshToken refreshToken = null;
        if (existingAccessToken != null) {
            if (existingAccessToken.isExpired()) {
                if (existingAccessToken.getRefreshToken() != null) {
                    refreshToken = existingAccessToken.getRefreshToken();
                    // The token store could remove the refresh token when the
                    // access token is removed, but we want to
                    // be sure...
                    tokenStore.removeRefreshToken(refreshToken);
                }
                tokenStore.removeAccessToken(existingAccessToken);
            } else {
                // Re-store the access token in case the authentication has changed
                tokenStore.storeAccessToken(existingAccessToken, authentication);
                return existingAccessToken;
            }
        }

        // Only create a new refresh token if there wasn't an existing one
        // associated with an expired access token.
        // Clients might be holding existing refresh tokens, so we re-use it in
        // the case that the old access token
        // expired.
        if (refreshToken == null) {
            refreshToken = createRefreshToken(authentication);
        }
        // But the refresh token itself might need to be re-issued if it has
        // expired.
        else if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiring = (ExpiringOAuth2RefreshToken) refreshToken;
            if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
                refreshToken = createRefreshToken(authentication);
            }
        }

        OAuth2AccessToken accessToken = createAccessToken(authentication, refreshToken);
        tokenStore.storeAccessToken(accessToken, authentication);
        // In case it was modified
        refreshToken = accessToken.getRefreshToken();
        if (refreshToken != null) {
            tokenStore.storeRefreshToken(refreshToken, authentication);
        }
        return accessToken;

    }

    private OAuth2AccessToken createAccessToken(OAuth2Authentication authentication,
        OAuth2RefreshToken refreshToken) {
        String tokenValue =
            new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII);
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(tokenValue);
        int validitySeconds = getAccessTokenValiditySeconds();
        if (validitySeconds > 0) {
            token.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
        }
        token.setRefreshToken(refreshToken);
        token.setScope(authentication.getOAuth2Request().getScope());

        return token;
    }

    private OAuth2RefreshToken createRefreshToken(OAuth2Authentication authentication) {
        if (!isSupportRefreshToken(authentication)) {
            return null;
        }
        int validitySeconds = getRefreshTokenValiditySeconds(authentication);
        String tokenValue =
            new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII);
        if (validitySeconds > 0) {
            return new DefaultExpiringOAuth2RefreshToken(tokenValue,
                new Date(System.currentTimeMillis()
                    + (validitySeconds * 1000L)));
        }
        return new DefaultOAuth2RefreshToken(tokenValue);
    }

    @Transactional(noRollbackFor = IllegalStateException.class)
    public OAuth2AccessToken refreshAccessToken(String refreshTokenValue,
        OAuth2Request tokenRequest) throws AuthenticationException {

        if (!supportRefreshToken) {
            throw new IllegalStateException("Invalid refresh token");
        }

        OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(refreshTokenValue);
        if (refreshToken == null) {
            throw new IllegalStateException("Invalid refresh token");
        }

        OAuth2Authentication authentication =
            tokenStore.readAuthenticationForRefreshToken(refreshToken);
        if (this.authenticationManager != null) {
            // The client has already been authenticated, but the user authentication might be old now, so give it a
            // chance to re-authenticate.
            Authentication userAuthentication = authentication.getUserAuthentication();
            PreAuthenticatedAuthenticationToken preAuthenticatedToken =
                new PreAuthenticatedAuthenticationToken(
                    userAuthentication,
                    "",
                    authentication.getAuthorities()
                );
            if (userAuthentication.getDetails() != null) {
                preAuthenticatedToken.setDetails(userAuthentication.getDetails());
            }
            Authentication user = authenticationManager.authenticate(preAuthenticatedToken);
            Object details = authentication.getDetails();
            authentication = new OAuth2Authentication(authentication.getOAuth2Request(), user);
            authentication.setDetails(details);
        }

        // clear out any access tokens already associated with the refresh
        // token.
        tokenStore.removeAccessTokenUsingRefreshToken(refreshToken);

        if (isExpired(refreshToken)) {
            tokenStore.removeRefreshToken(refreshToken);
            throw new IllegalStateException("Invalid refresh token (expired)");
        }

        authentication = createRefreshedAuthentication(authentication, tokenRequest);

        if (!reuseRefreshToken) {
            tokenStore.removeRefreshToken(refreshToken);
            refreshToken = createRefreshToken(authentication);
        }

        OAuth2AccessToken accessToken = createAccessToken(authentication, refreshToken);
        tokenStore.storeAccessToken(accessToken, authentication);
        if (!reuseRefreshToken) {
            tokenStore.storeRefreshToken(accessToken.getRefreshToken(), authentication);
        }
        return accessToken;
    }

    /**
     * Create a refreshed authentication.
     *
     * @param authentication The authentication.
     * @param request        The scope for the refreshed token.
     * @return The refreshed authentication.
     * @throws IllegalStateException If the scope requested is invalid or wider than the original
     *                               scope.
     */
    private OAuth2Authentication createRefreshedAuthentication(OAuth2Authentication authentication,
        OAuth2Request request) {
        OAuth2Authentication narrowed = authentication;
        Set<String> scope = request.getScope();
        OAuth2Request clientAuth = authentication.getOAuth2Request().refresh(request);
        if (scope != null && !scope.isEmpty()) {
            Set<String> originalScope = clientAuth.getScope();
            if (originalScope == null || !originalScope.containsAll(scope)) {
                throw new IllegalStateException(
                    "Unable to narrow the scope of the authentication: " + originalScope);
            } else {
                clientAuth = clientAuth.narrowScope(scope);
            }
        }
        narrowed = new OAuth2Authentication(clientAuth, authentication.getUserAuthentication());
        return narrowed;
    }

    protected boolean isExpired(OAuth2RefreshToken refreshToken) {
        if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiringToken = (ExpiringOAuth2RefreshToken) refreshToken;
            return expiringToken.getExpiration() == null
                || System.currentTimeMillis() > expiringToken.getExpiration().getTime();
        }
        return false;
    }


    /**
     * Is a refresh token supported.
     *
     * @param clientAuth the current authorization request
     * @return boolean to indicate if refresh token is supported
     */
    protected boolean isSupportRefreshToken(OAuth2Authentication clientAuth) {
        return this.supportRefreshToken;
    }

    /**
     * The refresh token validity period in seconds
     *
     * @param authentication the current authorization request
     * @return the refresh token validity period in seconds
     */
    protected int getRefreshTokenValiditySeconds(OAuth2Authentication authentication) {
        return refreshTokenValiditySeconds;
    }

    /**
     * The access token validity period in seconds
     *
     * @return the access token validity period in seconds
     */
    protected int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    /**
     * The persistence strategy for token storage.
     *
     * @param tokenStore the store for access and refresh tokens.
     */
    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    /**
     * An authentication manager that will be used (if provided) to check the user authentication
     * when a token is refreshed.
     *
     * @param authenticationManager the authenticationManager to set
     */
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}
