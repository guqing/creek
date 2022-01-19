package xyz.guqing.creek.security;

import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Collection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * Jwt authentication convert.
 *
 * @author guqing
 * @date 2022-01-19
 * @see JwtGrantedAuthoritiesConverter
 */
public class JwtAuthenticationConverter implements Converter<DecodedJWT,
    AbstractAuthenticationToken> {

    private Converter<DecodedJWT, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter =
        new JwtGrantedAuthoritiesConverter();

    private String principalClaimName = PublicClaims.SUBJECT;

    @Override
    public final AbstractAuthenticationToken convert(@NonNull DecodedJWT jwt) {
        Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(jwt);

        String principalClaimValue = jwt.getClaim(this.principalClaimName).asString();
        return new JwtAuthenticationToken(jwt, authorities, principalClaimValue);
    }

    /**
     * Sets the {@link Converter Converter&lt;Jwt, Collection&lt;GrantedAuthority&gt;&gt;} to use.
     * Defaults to {@link JwtGrantedAuthoritiesConverter}.
     *
     * @param jwtGrantedAuthoritiesConverter The converter
     * @see JwtGrantedAuthoritiesConverter
     */
    public void setJwtGrantedAuthoritiesConverter(
        Converter<DecodedJWT, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
        Assert.notNull(jwtGrantedAuthoritiesConverter,
            "jwtGrantedAuthoritiesConverter cannot be null");
        this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
    }

    /**
     * Sets the principal claim name. Defaults to {@link JwtClaimNames#SUB}.
     *
     * @param principalClaimName The principal claim name
     * @since 5.4
     */
    public void setPrincipalClaimName(String principalClaimName) {
        Assert.hasText(principalClaimName, "principalClaimName cannot be empty");
        this.principalClaimName = principalClaimName;
    }

}
