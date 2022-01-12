package xyz.guqing.creek.config.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import xyz.guqing.creek.exception.AuthenticationException;
import xyz.guqing.creek.security.AccessToken;
import xyz.guqing.creek.security.properties.SecurityProperties;
import xyz.guqing.creek.security.properties.TokenProperties;

/**
 * @author guqing
 * @date 2019/8/10
 */
@Slf4j
@Component
@EnableConfigurationProperties({SecurityProperties.class})
public class JwtTokenProvider implements Serializable {

    private static final long serialVersionUID = -5625635588908941275L;
    private static final Algorithm algorithm = Algorithm.HMAC256("secret");
    private final TokenProperties tokenProperties;

    @Value(value = "${auth0.apiAudience}")
    private String audience;
    @Value(value = "${auth0.issuer}")
    private String issuer;

    public JwtTokenProvider(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    private String generateToken(Authentication authentication, Date expireAt) {
        String scope = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
        return JWT.create()
            // 签发者
            .withIssuer(issuer)
            .withAudience(audience)
            // Authentication#getName maps to the JWT’s sub property, if one is present.
            .withSubject(authentication.getName())
            .withClaim("scope", scope)
            // 签发日期
            .withIssuedAt(new Date())
            // 过期
            .withExpiresAt(expireAt)
            .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            JWTVerifier verifier = getJwtVerifier();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            return null;
        }
    }

    public JWTVerifier getJwtVerifier() {
        return JWT.require(algorithm)
            .withAudience(audience)
            .withIssuer(issuer)
            .acceptLeeway(1)
            .build(); //Reusable verifier instance
    }

    public AccessToken getToken(Authentication authentication) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, tokenProperties.getExpireAt());

        String token = generateToken(authentication, cal.getTime());

        // 偏移过期时间生成refreshToken
        cal.add(Calendar.MINUTE, 30);
        String refreshToken = generateToken(authentication, cal.getTime());
        AccessToken accessToken = new AccessToken(token);
        accessToken.setRefreshToken(refreshToken);
        accessToken.setExpiration(cal.getTimeInMillis());
        accessToken.setTokenType(tokenProperties.getTokenPrefix().toLowerCase());
        return accessToken;
    }

    public AccessToken refreshToken(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DecodedJWT decodedJWT = verifyToken(token);
        if (decodedJWT == null) {
            throw new AuthenticationException("登录已过期");
        }
        return getToken(authentication);
    }
}
