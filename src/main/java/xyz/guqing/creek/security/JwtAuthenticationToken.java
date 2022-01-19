package xyz.guqing.creek.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Collection;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.Transient;
import xyz.guqing.creek.security.token.AbstractOAuth2TokenAuthenticationToken;

/**
 * An implementation of an {@link AbstractOAuth2TokenAuthenticationToken} representing a {@link
 * DecodedJWT} {@code Authentication}.
 *
 * @author Joe Grandja
 * @see AbstractOAuth2TokenAuthenticationToken
 * @see DecodedJWT
 * @since 5.1
 */
@Slf4j
@Transient
public class JwtAuthenticationToken extends AbstractOAuth2TokenAuthenticationToken<DecodedJWT> {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final String name;

    /**
     * Constructs a {@code JwtAuthenticationToken} using the provided parameters.
     *
     * @param token the JWT token
     */
    public JwtAuthenticationToken(DecodedJWT token) {
        super(token);
        this.name = token.getSubject();
    }

    /**
     * Constructs a {@code JwtAuthenticationToken} using the provided parameters.
     *
     * @param jwt         the JWT
     * @param authorities the authorities assigned to the JWT
     */
    public JwtAuthenticationToken(DecodedJWT jwt,
        Collection<? extends GrantedAuthority> authorities) {
        super(jwt, authorities);
        this.setAuthenticated(true);
        this.name = jwt.getSubject();
    }

    /**
     * Constructs a {@code JwtAuthenticationToken} using the provided parameters.
     *
     * @param jwt         the JWT
     * @param authorities the authorities assigned to the JWT
     * @param name        the principal name
     */
    public JwtAuthenticationToken(DecodedJWT jwt,
        Collection<? extends GrantedAuthority> authorities, String name) {
        super(jwt, authorities);
        this.setAuthenticated(true);
        this.name = name;
    }

    public static JwtAuthenticationToken withToken(String token) {
        if (token == null) {
            log.debug("No token was provided to build {}", JwtAuthenticationToken.class.getName());
            return null;
        } else {
            try {
                DecodedJWT jwt = JWT.decode(token);
                return new JwtAuthenticationToken(jwt);
            } catch (JWTDecodeException var2) {
                log.debug("Failed to decode token as jwt", var2);
                return null;
            }
        }
    }

    @Override
    public Map<String, Claim> getTokenAttributes() {
        return this.getToken().getClaims();
    }

    /**
     * The principal name which is, by default, the {@link DecodedJWT}'s subject
     */
    @Override
    public String getName() {
        return this.name;
    }
}
