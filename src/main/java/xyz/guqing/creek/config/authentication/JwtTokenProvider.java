package xyz.guqing.creek.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import xyz.guqing.creek.exception.AuthenticationException;
import xyz.guqing.creek.model.bo.MyUserDetails;
import xyz.guqing.creek.security.model.AccessToken;
import xyz.guqing.creek.security.properties.SecurityProperties;
import xyz.guqing.creek.security.properties.TokenProperties;

/**
 * @author guqing
 * @date 2019/8/10
 */
@Slf4j
@Component
@EnableConfigurationProperties({SecurityProperties.class})
public class JwtTokenUtils implements Serializable {
    private static final long serialVersionUID = -5625635588908941275L;
    private static final Algorithm algorithm = Algorithm.HMAC256("secret");
    private final TokenProperties tokenProperties;

    public JwtTokenUtils(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    public AccessToken generateAccessToken(String username) {
        String token = generateToken(username);
        String refreshToken = getRefreshToken(token);
        AccessToken accessToken = new AccessToken(token);
        accessToken.setRefreshToken(refreshToken);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, tokenProperties.getExpireAt());
        accessToken.setExpiration(cal.getTimeInMillis());
        accessToken.setTokenType(tokenProperties.getTokenPrefix().toLowerCase());
        return accessToken;
    }

    public AccessToken refreshToken(String token) {
        if (!canTokenBeRefreshed(token)) {
            throw new AuthenticationException("登录已过期");
        }
        String username = getUsernameFromToken(token);
        return generateAccessToken(username);
    }

    public static DecodedJWT verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                .acceptLeeway(1)
                .build(); //Reusable verifier instance
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            return null;
        }
    }

    public static String generateToken(String username) {
        long expireAtMs = System.currentTimeMillis() + expireAt;
        return generateToken(username, new Date(expireAtMs));
    }

    private static String generateToken(String username, Date expireAt) {
        return JWT.create()
            // 签发者
            .withIssuer(username)
            // 签发日期
            .withIssuedAt(new Date())
            // 过期
            .withExpiresAt(expireAt)
            .sign(algorithm);
    }

    public static String getRefreshToken(String token) {
        final DecodedJWT decodedJWT = verifyToken(token);
        if (decodedJWT == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(decodedJWT.getExpiresAt());
        cal.add(Calendar.MINUTE, 30);
        return generateToken(decodedJWT.getIssuer(), cal.getTime());
    }
}
