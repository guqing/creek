package xyz.guqing.creek.security.store;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author guqing
 * @since 2022-01-25
 */
@Data
public class OAuth2Request {
    /**
     * Will be non-null if the request is for a token to be refreshed.
     */
    private OAuth2Request refresh = null;

    /**
     * Resolved scope set, initialized (by the OAuth2RequestFactory) with the
     * scopes originally requested. Further processing and user interaction may
     * alter the set of scopes that is finally granted and stored when the
     * request processing is complete.
     */
    private Set<String> scope = new HashSet<>();

    /**
     * Resolved granted authorities for this request. May change during request processing.
     */
    private Collection<? extends GrantedAuthority> authorities = new HashSet<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public OAuth2Request(Collection<? extends GrantedAuthority> authorities, Set<String> scope) {
        setScope(scope);
        if (authorities != null) {
            this.authorities = new HashSet<GrantedAuthority>(authorities);
        }
    }

    protected OAuth2Request(OAuth2Request other) {
        this(other.getAuthorities(), other.getScope());
    }

    public OAuth2Request narrowScope(Set<String> scope) {
        OAuth2Request request = new OAuth2Request(authorities, scope);
        request.refresh = this.refresh;
        return request;
    }

    public OAuth2Request refresh(OAuth2Request tokenRequest) {
        OAuth2Request request = new OAuth2Request(authorities, getScope());
        request.refresh = tokenRequest;
        return request;
    }

    /**
     * @return true if this request is known to be for a token to be refreshed
     */
    public boolean isRefresh() {
        return refresh != null;
    }

    protected void setScope(Collection<String> scope) {
        if (scope != null && scope.size() == 1) {
            String value = scope.iterator().next();
            /*
             * This is really an error, but it can catch out unsuspecting users
             * and it's easy to fix. It happens when an AuthorizationRequest
             * gets bound accidentally from request parameters using
             * @ModelAttribute.
             */
            if (value.contains(" ") || value.contains(",")) {
                scope = parseParameterList(value);
            }
        }
        this.scope = Collections
            .unmodifiableSet(scope == null ? new LinkedHashSet<>()
                : new LinkedHashSet<>(scope));
    }

    public static Set<String> parseParameterList(String values) {
        Set<String> result = new TreeSet<>();
        if (values != null && values.trim().length() > 0) {
            // the spec says the scope is separated by spaces
            String[] tokens = values.split("[\\s+]");
            result.addAll(Arrays.asList(tokens));
        }
        return result;
    }
}
