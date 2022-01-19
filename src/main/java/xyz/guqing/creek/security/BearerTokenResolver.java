package xyz.guqing.creek.security;

import javax.servlet.http.HttpServletRequest;
import xyz.guqing.creek.security.token.OAuth2AuthenticationException;

/**
 * A strategy for resolving
 * <a href="https://tools.ietf.org/html/rfc6750#section-1.2" target="_blank">Bearer
 * Token</a>s from the {@link HttpServletRequest}.
 *
 * @author Vedran Pavic
 * @since 5.1
 * @see <a href="https://tools.ietf.org/html/rfc6750#section-2" target="_blank">RFC 6750
 * Section 2: Authenticated Requests</a>
 */
@FunctionalInterface
public interface BearerTokenResolver {

    /**
     * Resolve any
     * <a href="https://tools.ietf.org/html/rfc6750#section-1.2" target="_blank">Bearer
     * Token</a> value from the request.
     * @param request the request
     * @return the Bearer Token value or {@code null} if none found
     * @throws OAuth2AuthenticationException if the found token is invalid
     */
    String resolve(HttpServletRequest request);

}
