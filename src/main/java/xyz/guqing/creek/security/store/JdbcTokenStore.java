package xyz.guqing.creek.security.store;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import xyz.guqing.creek.mapper.OauthAccessTokenMapper;
import xyz.guqing.creek.model.entity.OauthAccessToken;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class JdbcTokenStore implements TokenStore {

    private final OauthAccessTokenMapper oauthAccessTokenMapper;

    public JdbcTokenStore(OauthAccessTokenMapper oauthAccessTokenMapper) {
        this.oauthAccessTokenMapper = oauthAccessTokenMapper;
    }

    private AuthenticationKeyGenerator authenticationKeyGenerator;

    public void setAuthenticationKeyGenerator(
        AuthenticationKeyGenerator authenticationKeyGenerator) {
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
        } catch (IllegalArgumentException e) {
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
        OauthAccessToken oauthAccessToken = new OauthAccessToken();
        oauthAccessToken.setTokenId(extractTokenKey(token.getValue()));
        oauthAccessToken.setToken(serializeAccessToken(token));
        oauthAccessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        oauthAccessToken.setAuthentication(serializeAuthentication(authentication));
        oauthAccessToken.setUserName(
            authentication.isClientOnly() ? null : authentication.getName());
        oauthAccessToken.setRefreshToken(extractTokenKey(refreshToken));

        oauthAccessTokenMapper.insert(oauthAccessToken);
    }

    protected String serializeAuthentication(OAuth2Authentication authentication) {
        return new String(SerializationUtils.serialize(authentication));
    }

    protected String serializeAccessToken(OAuth2AccessToken token) {
        return new String(SerializationUtils.serialize(token));
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;

        //select token_id, token from oauth_access_token where token_id = ?
        try {
            // query access token
            OauthAccessToken oauthAccessToken =
                oauthAccessTokenMapper.selectById(extractTokenKey(tokenValue));
            accessToken = deserializeAccessToken(oauthAccessToken.getToken());
        } catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize access token", e);
            removeAccessToken(tokenValue);
        }
        return accessToken;
    }

    private byte[] getBytes(String token) {
        return token == null ? null : token.getBytes(StandardCharsets.UTF_8);
    }

    protected OAuth2AccessToken deserializeAccessToken(String token) {
        return SerializationUtils.deserialize(getBytes(token));
    }

    protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
        return SerializationUtils.deserialize(token);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        removeAccessToken(token.getValue());
    }

    public void removeAccessToken(String tokenValue) {
        // delete from oauth_access_token where token_id = ?
        oauthAccessTokenMapper.deleteById(extractTokenKey(tokenValue));
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken,
        OAuth2Authentication authentication) {
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
        } catch (IllegalArgumentException e) {
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
        return readAuthenticationForRefreshToken(token.getValue());
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
        OAuth2Authentication authentication = null;

        try {
            // select token_id, authentication from oauth_refresh_token where token_id = ?
//            authentication = jdbcTemplate.queryForObject(selectRefreshTokenAuthenticationSql,
//                    new RowMapper<OAuth2Authentication>() {
//                        public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
//                            return deserializeAuthentication(rs.getBytes(2));
//                        }
//                    }, extractTokenKey(value));
        } catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize access token", e);
            removeRefreshToken(value);
        }

        return authentication;
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
        // select token_id, token from oauth_access_token where authentication_id = ?
        try {
            OauthAccessToken oauthAccessToken =
                oauthAccessTokenMapper.selectFromAuthentication(key);
            accessToken = deserializeAccessToken(oauthAccessToken.getToken());
        } catch (IllegalArgumentException e) {
            log.error("Could not extract access token for authentication " + authentication, e);
        }
        if (accessToken != null) {
            OAuth2Authentication oldAuthentication = readAuthentication(accessToken.getValue());
            if (oldAuthentication == null || !key.equals(
                authenticationKeyGenerator.extractKey(oldAuthentication))) {
                removeAccessToken(accessToken.getValue());
                // Keep the store consistent (maybe the same user is represented by this authentication but the details have
                // changed)
                storeAccessToken(accessToken, authentication);
            }
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByUserName(String username) {
        List<OAuth2AccessToken> accessTokens = oauthAccessTokenMapper.selectByUsername(username)
            .stream()
            .map(OauthAccessToken::getToken)
            .filter(Objects::nonNull)
            .map(this::deserializeAccessToken)
            .collect(Collectors.toList());

        return removeNulls(accessTokens);
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
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                "MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(
                "UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }

}
