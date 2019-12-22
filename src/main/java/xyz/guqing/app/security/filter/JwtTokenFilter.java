package xyz.guqing.app.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.guqing.app.security.properties.SecurityProperties;
import xyz.guqing.app.security.properties.TokenProperties;
import xyz.guqing.app.security.support.MyUserDetails;
import xyz.guqing.app.security.support.MyUserDetailsServiceImpl;
import xyz.guqing.app.security.utils.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@EnableConfigurationProperties({SecurityProperties.class})
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsService;


    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        TokenProperties tokenProperties = securityProperties.getTokenProperties();

        String authTokenHeader = request.getHeader(tokenProperties.getHeaderString());
        if (authTokenHeader != null) {
            String username = jwtTokenUtil.getUsernameFromToken(authTokenHeader);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                MyUserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);

                if (jwtTokenUtil.validateToken(authTokenHeader, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else if(jwtTokenUtil.canTokenBeRefreshed(authTokenHeader)){
                    // token过期了，需要刷新token
                    String newToken = jwtTokenUtil.generateToken(userDetails);
                    response.setHeader(tokenProperties.getHeaderString(), newToken);
                }
            }
        }
        chain.doFilter(request, response);
    }
}