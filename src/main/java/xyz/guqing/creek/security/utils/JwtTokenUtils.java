package xyz.guqing.creek.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import xyz.guqing.creek.model.bo.MyUserDetails;
import xyz.guqing.creek.model.entity.User;
import xyz.guqing.creek.security.model.AccessToken;
import xyz.guqing.creek.security.properties.SecurityProperties;
import xyz.guqing.creek.security.properties.TokenProperties;
import xyz.guqing.creek.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guqing
 * @date 2019/8/10
 */
@Slf4j
@Component
@EnableConfigurationProperties({SecurityProperties.class})
public class JwtTokenUtils implements Serializable {
    private static final long serialVersionUID = -5625635588908941275L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    private final TokenProperties tokenProperties;

    public JwtTokenUtils(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    public AccessToken generateAccessToken(String username) {
        String token = generateToken(username);
        String refreshToken = refreshToken(username);
        AccessToken accessToken = new AccessToken(token);
        accessToken.setRefreshToken(refreshToken);
        long expirationTime = tokenProperties.getExpirationTime();
        accessToken.setExpiration(expirationTime);
        accessToken.setTokenType(tokenProperties.getTokenPrefix().toLowerCase());
        return accessToken;
    }

    public String getUsernameFromToken(String token) {
        if(StringUtils.isBlank(token)) {
            return null;
        }
        try {
            final Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            log.debug("从token获取用户名出错,参数:{}",token);
            return null;
        }
    }

    public Date getCreatedDateFromToken(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            return null;
        }
    }

    public Date getExpirationDateFromToken(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return claims.getExpiration();
        } catch (Exception e) {
            return null;
        }
    }

    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(tokenProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("解析token出错,参数:{}",token);
            claims = null;
        }

        return claims;
    }

    private Date generateTokenExpirationDate() {
        return new Date(System.currentTimeMillis() + tokenProperties.getExpirationTime() * 1000);
    }

    private Date generateRefreshExpirationDate() {
        long refreshTokenExpireSeconds = tokenProperties.getExpirationTime() + tokenProperties.getAllowedClockSkewSeconds();
        long refreshTokenExpireMillis = System.currentTimeMillis() +  refreshTokenExpireSeconds * 1000;
        return new Date(refreshTokenExpireMillis);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if(expiration != null) {
            //判断是否过期
            return expiration.before(new Date());
        }
        //获取不到expiration肯定过期
        return true;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(MyUserDetails userDetails) {
        Map<String, Object> claims = getClaimsMap(userDetails.getUsername());
        return generateToken(claims);
    }

    private Map<String, Object> getClaimsMap(String username) {
        Map<String, Object> claims = new HashMap<>(3, 1);
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return claims;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = getClaimsMap(user.getUsername());
        return generateToken(claims);
    }

    public String generateToken(String username) {
        Map<String, Object> claims = getClaimsMap(username);
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return generateToken(claims, generateTokenExpirationDate());
    }

    private String generateRefreshToken(Map<String, Object> claims) {
        return generateToken(claims, generateRefreshExpirationDate());
    }

    private String generateToken(Map<String, Object> claims, Date expireDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, tokenProperties.getSecret())
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if(expiration != null && expiration.before(new Date())) {
            //判断是否可以刷新
            return true;
        }
        //获取不到expiration肯定过期并且不能刷新
        return false;
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateRefreshToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        MyUserDetails myUserDetails = (MyUserDetails) userDetails;
        final String username = getUsernameFromToken(token);

        return (
                username.equals(myUserDetails.getUsername())
                        && !isTokenExpired(token)
        );
    }
}
