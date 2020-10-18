package xyz.guqing.creek.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.guqing.creek.model.bo.MyUserDetails;
import xyz.guqing.creek.security.properties.SecurityProperties;
import xyz.guqing.creek.security.properties.TokenProperties;
import xyz.guqing.creek.security.support.MyUserDetailsServiceImpl;
import xyz.guqing.creek.security.utils.JwtTokenUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author guqing
 * @date 2019-12-22 24:53
 */
@Component
@EnableConfigurationProperties({SecurityProperties.class})
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        TokenProperties tokenProperties = securityProperties.getTokenProperties();
        String tokenHeader = tokenProperties.getHeaderString();
        String authTokenHeader = request.getHeader(tokenHeader);
        if (authTokenHeader != null) {
            // 从header的值中解析token
            String token = parseToken(authTokenHeader);
            String username = jwtTokenUtils.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                MyUserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);

                if (jwtTokenUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else if (jwtTokenUtils.canTokenBeRefreshed(token)) {
                    // token过期了，需要刷新token
                    String newToken = jwtTokenUtils.generateToken(userDetails);
                    response.setHeader(tokenHeader, newToken);
                }
            }
        }
        chain.doFilter(request, response);
    }

    private String parseToken(String tokenHeader) {
        Assert.notNull(tokenHeader, "解析token出错，token不能为空");

        TokenProperties tokenProperties = securityProperties.getTokenProperties();
        String tokenPrefix = tokenProperties.getTokenPrefix();
        if (StringUtils.isBlank(tokenPrefix)) {
            return tokenHeader;
        }

        int tokenPrefixLength = tokenProperties.getTokenPrefix().length();
        String subToken = tokenHeader.substring(0, tokenPrefixLength);
        if (StringUtils.equalsIgnoreCase(subToken, tokenPrefix)) {
            return tokenHeader.substring(tokenPrefixLength + 1);
        }
        // 如果获取到的token不是以配置的token头开始则返回null,忽略token
        return null;
    }
}