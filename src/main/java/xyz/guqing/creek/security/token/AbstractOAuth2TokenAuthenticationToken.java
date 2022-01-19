package xyz.guqing.creek.security.token;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * Base class for {@link AbstractAuthenticationToken} implementations that expose common
 * attributes between different OAuth 2.0 Access Token Formats.
 *
 * <p>
 * For example, a {@link DecodedJWT} could expose its {@link DecodedJWT#getClaims()} via
 * {@link #getTokenAttributes()} or an &quot;Introspected&quot; OAuth 2.0 Access Token
 * could expose the attributes of the Introspection Response via
 * {@link #getTokenAttributes()}.
 *
 * @author Joe Grandja
 * @author guqing
 * @see DecodedJWT
 * @see <a href="https://tools.ietf.org/search/rfc7662#section-2.2" target="_blank" >2.2
 * Introspection Response</a>
 */
public abstract class AbstractOAuth2TokenAuthenticationToken<T>
    extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private final Object credentials;

    private final T token;

    /**
     * Sub-class constructor.
     */
    protected AbstractOAuth2TokenAuthenticationToken(T token) {

        this(token, null);
    }

    /**
     * Sub-class constructor.
     * @param authorities the authorities assigned to the Access Token
     */
    protected AbstractOAuth2TokenAuthenticationToken(T token, Collection<? extends GrantedAuthority> authorities) {
        this(token, token, token, authorities);
    }

    protected AbstractOAuth2TokenAuthenticationToken(T token, Object principal, Object credentials,
        Collection<? extends GrantedAuthority> authorities) {

        super(authorities);
        Assert.notNull(token, "token cannot be null");
        Assert.notNull(principal, "principal cannot be null");
        this.principal = principal;
        this.credentials = credentials;
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    /**
     * Get the token bound to this {@link Authentication}.
     */
    public final T getToken() {
        return this.token;
    }

    /**
     * Returns the attributes of the access token.
     * @return a {@code Map} of the attributes in the access token.
     */
    public abstract Map<String, Claim> getTokenAttributes();

}
