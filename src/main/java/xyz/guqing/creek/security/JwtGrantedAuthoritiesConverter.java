package xyz.guqing.creek.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Extracts the {@link GrantedAuthority}s from scope attributes typically found in a {@link Jwt}.
 *
 * @author guqing
 * @date 2022-01-19
 */
public final class JwtGrantedAuthoritiesConverter implements
    Converter<DecodedJWT, Collection<GrantedAuthority>> {

    private static final String DEFAULT_AUTHORITY_PREFIX = "SCOPE_";
    private static final Collection<String> WELL_KNOWN_AUTHORITIES_CLAIM_NAMES =
        Arrays.asList("scope", "scp");
    private final Log logger = LogFactory.getLog(getClass());
    private String authorityPrefix = DEFAULT_AUTHORITY_PREFIX;

    private String authoritiesClaimName;

    /**
     * Extract {@link GrantedAuthority}s from the given {@link DecodedJWT}.
     *
     * @param jwt The {@link DecodedJWT} token
     * @return The {@link GrantedAuthority authorities} read from the token scopes
     */
    @Override
    public Collection<GrantedAuthority> convert(@NonNull DecodedJWT jwt) {
        return getScopeAuthorities(jwt);
    }

    /**
     * Sets the prefix to use for {@link GrantedAuthority authorities} mapped by this converter.
     * Defaults to {@link JwtGrantedAuthoritiesConverter#DEFAULT_AUTHORITY_PREFIX}.
     *
     * @param authorityPrefix The authority prefix
     */
    public void setAuthorityPrefix(String authorityPrefix) {
        Assert.notNull(authorityPrefix, "authorityPrefix cannot be null");
        this.authorityPrefix = authorityPrefix;
    }

    /**
     * Sets the name of token claim to use for mapping {@link GrantedAuthority authorities} by this
     * converter. Defaults to {@link JwtGrantedAuthoritiesConverter#WELL_KNOWN_AUTHORITIES_CLAIM_NAMES}.
     *
     * @param authoritiesClaimName The token claim name to map authorities
     */
    public void setAuthoritiesClaimName(String authoritiesClaimName) {
        Assert.hasText(authoritiesClaimName, "authoritiesClaimName cannot be empty");
        this.authoritiesClaimName = authoritiesClaimName;
    }

    private String getAuthoritiesClaimName(DecodedJWT jwt) {
        if (this.authoritiesClaimName != null) {
            return this.authoritiesClaimName;
        }
        for (String claimName : WELL_KNOWN_AUTHORITIES_CLAIM_NAMES) {
            if (jwt.getClaim(claimName) != null) {
                return claimName;
            }
        }
        return null;
    }

    private Collection<GrantedAuthority> getScopeAuthorities(DecodedJWT decoded) {
        String claimName = getAuthoritiesClaimName(decoded);
        if (claimName == null) {
            this.logger.trace(
                "Returning no authorities since could not find any claims that might contain scopes");
            return Collections.emptyList();
        }

        String scope = decoded.getClaim(claimName).asString();
        if (!StringUtils.hasText(scope)) {
            this.logger.trace(
                "Returning no authorities since could not find any claims that might contain scopes");
            return Collections.emptyList();
        }
        final String[] scopes = scope.split(" ");
        Collection<GrantedAuthority> authorities = new ArrayList<>(scopes.length * 2);
        for (String authority : scopes) {
            // For backwards-compatibility, create authority without scope prefix
            authorities.add(new SimpleGrantedAuthority(authority));
            authorities.add(new SimpleGrantedAuthority(this.authorityPrefix + authority));
        }
        return authorities;
    }

}
