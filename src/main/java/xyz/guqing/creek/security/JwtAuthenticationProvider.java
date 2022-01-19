package xyz.guqing.creek.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 * @author guqing
 * @date 2022-01-19
 */
@Slf4j
public final class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JWTVerifier jwtVerifier;

    private Converter<DecodedJWT, ? extends AbstractAuthenticationToken>
        jwtAuthenticationConverter = new JwtAuthenticationConverter();

    public JwtAuthenticationProvider(JWTVerifier jwtVerifier) {
        Assert.notNull(jwtVerifier, "jwtVerifier cannot be null");
        this.jwtVerifier = jwtVerifier;
    }

    /**
     * Decode and validate the
     * <a href="https://tools.ietf.org/html/rfc6750#section-1.2" target="_blank">Bearer
     * Token</a>.
     *
     * @param authentication the authentication request object.
     * @return A successful authentication
     * @throws AuthenticationException if authentication failed for some reason
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws
        AuthenticationException {
        JwtAuthenticationToken bearer = (JwtAuthenticationToken) authentication;
        DecodedJWT jwt = getJwt(bearer);
        AbstractAuthenticationToken token = this.jwtAuthenticationConverter.convert(jwt);
        if (token != null) {
            token.setDetails(bearer.getDetails());
        }
        log.debug("Authenticated token");
        return token;
    }

    private DecodedJWT getJwt(JwtAuthenticationToken bearer) {
        try {
            return jwtVerifier.verify(bearer.getToken());
        } catch (InvalidClaimException failed) {
            log.debug("Failed to authenticate since the JWT was invalid");
            throw new InvalidBearerTokenException(failed.getMessage(), failed);
        } catch (JWTVerificationException failed) {
            throw new AuthenticationServiceException(failed.getMessage(), failed);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setJwtAuthenticationConverter(
        Converter<DecodedJWT, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter) {
        Assert.notNull(jwtAuthenticationConverter, "jwtAuthenticationConverter cannot be null");
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }
}
