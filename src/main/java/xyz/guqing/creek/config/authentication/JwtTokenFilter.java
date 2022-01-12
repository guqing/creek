package xyz.guqing.creek.config.authentication;

import com.auth0.spring.security.api.authentication.PreAuthenticatedAuthenticationJsonWebToken;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.guqing.creek.config.authentication.JwtTokenProvider;
import xyz.guqing.creek.security.properties.SecurityProperties;
import xyz.guqing.creek.security.support.MyUserDetailsServiceImpl;

/**
 * @author guqing
 * @date 2019-12-22 24:53
 */
@EnableConfigurationProperties({SecurityProperties.class})
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain chain) throws ServletException, IOException {
        String token = tokenFromRequest(request);

        if (token != null) {
            // 从header的值中解析token
            Authentication authentication =
                PreAuthenticatedAuthenticationJsonWebToken.usingToken(token)
                    .verify(tokenProvider.getJwtVerifier());
//            if (decodedJWT != null) {
//                UserDetails userDetails =
//                    this.myUserDetailsService.loadUserByUsername(decodedJWT.getSubject());

//                UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
//                    request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
        }
        chain.doFilter(request, response);
    }

    private String tokenFromRequest(HttpServletRequest request) {
        String value = request.getHeader("Authorization");
        if (value != null && value.toLowerCase().startsWith("bearer")) {
            String[] parts = value.split(" ");
            return parts.length < 2 ? null : parts[1].trim();
        } else {
            return null;
        }
    }
}
