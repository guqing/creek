package xyz.guqing.creek.security.store;

import lombok.extern.slf4j.Slf4j;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class JdbcTokenStore implements TokenStore {
    private AuthenticationKeyGenerator authenticationKeyGenerator;

    public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
        this.authenticationKeyGenerator = authenticationKeyGenerator;
    }


    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        OAuth2Authentication authentication = null;

        try {
            //select token_id, authentication from oauth_access_token where token_id = ?
        }
        catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize authentication", e);
            removeAccessToken(token);
        }

        return authentication;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }

        if (readAccessToken(token.getValue()) != null) {
            removeAccessToken(token.getValue());
        }
        //table: oauth_access_token
        // token_id: extractTokenKey(token.getValue()),
        // token: new SqlLobValue(serializeAccessToken(token)),
        // authentication_id: authenticationKeyGenerator.extractKey(authentication)
        // user_name: authentication.isClientOnly() ? null : authentication.getName(),
        // client_id: authentication.getOAuth2Request().getClientId()
        // authentication: new SqlLobValue(serializeAuthentication(authentication))
        // refresh_token: extractTokenKey(refreshToken)
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;

        //select token_id, token from oauth_access_token where token_id = ?
        try {
            // query access token
        }
        catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize access token", e);
            removeAccessToken(tokenValue);
        }

        return accessToken;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        removeAccessToken(token.getValue());
    }

    public void removeAccessToken(String tokenValue) {
        //jdbcTemplate.update(deleteAccessTokenSql, extractTokenKey(tokenValue));
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        // jdbcTemplate.update(insertRefreshTokenSql, new Object[] { extractTokenKey(refreshToken.getValue()),
        //				new SqlLobValue(serializeRefreshToken(refreshToken)),
        //				new SqlLobValue(serializeAuthentication(authentication)) }, new int[] { Types.VARCHAR, Types.BLOB,
        //				Types.BLOB });
    }


    @Override
    public OAuth2RefreshToken readRefreshToken(String token) {
        OAuth2RefreshToken refreshToken = null;

        try {
//            refreshToken = jdbcTemplate.queryForObject(selectRefreshTokenSql, new RowMapper<OAuth2RefreshToken>() {
//                public OAuth2RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    return deserializeRefreshToken(rs.getBytes(2));
//                }
//            }, extractTokenKey(token));
        }
        catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize refresh token", e);
            removeRefreshToken(token);
        }

        return refreshToken;
    }

    public void removeRefreshToken(String token) {
        //jdbcTemplate.update(deleteRefreshTokenSql, extractTokenKey(token));
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        removeRefreshToken(token.getValue());
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    public void removeAccessTokenUsingRefreshToken(String refreshToken) {
//        jdbcTemplate.update(deleteAccessTokenFromRefreshTokenSql, new Object[] { extractTokenKey(refreshToken) },
//                new int[] { Types.VARCHAR });
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;

        String key = authenticationKeyGenerator.extractKey(authentication);
        //select token_id, token from oauth_access_token where authentication_id = ?

        if (accessToken != null) {
            OAuth2Authentication oldAuthentication = readAuthentication(accessToken.getValue());
            if (oldAuthentication == null || !key.equals(authenticationKeyGenerator.extractKey(oldAuthentication))) {
                removeAccessToken(accessToken.getValue());
                // Keep the store consistent (maybe the same user is represented by this authentication but the details have
                // changed)
                storeAccessToken(accessToken, authentication);
            }
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByUsername(String username) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<>();

//            accessTokens = jdbcTemplate.query(selectAccessTokensFromUserNameAndClientIdSql, new SafeAccessTokenRowMapper(),
//                    userName, clientId);
        accessTokens = removeNulls(accessTokens);

        return accessTokens;
    }

    private List<OAuth2AccessToken> removeNulls(List<OAuth2AccessToken> accessTokens) {
        List<OAuth2AccessToken> tokens = new ArrayList<>();
        for (OAuth2AccessToken token : accessTokens) {
            if (token != null) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }

}
