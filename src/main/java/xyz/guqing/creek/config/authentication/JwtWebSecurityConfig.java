package xyz.guqing.creek.config.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.guqing.creek.security.JwtAuthenticationProvider;
import xyz.guqing.creek.security.handler.JwtAuthenticationFailureHandler;
import xyz.guqing.creek.security.handler.JwtLogoutSuccessHandler;
import xyz.guqing.creek.security.properties.LoginProperties;
import xyz.guqing.creek.security.properties.SecurityProperties;
import xyz.guqing.creek.security.support.MyUserDetailsServiceImpl;
import xyz.guqing.creek.security.token.BearerTokenAuthenticationEntryPoint;
import xyz.guqing.creek.security.token.BearerTokenAuthenticationFilter;

/**
 * @author guqing
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({SecurityProperties.class})
public class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final Algorithm algorithm = Algorithm.HMAC256("secret-123");

    private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
    private final MyUserDetailsServiceImpl userDetailsService;
    private final LoginProperties loginProperties;

    @Value(value = "${auth0.apiAudience}")
    private String audience;

    @Value(value = "${auth0.issuer}")
    private String issuer;

    private AuthenticationManager authenticationManager;

    public JwtWebSecurityConfig(SecurityProperties securityProperties,
        JwtLogoutSuccessHandler jwtLogoutSuccessHandler,
        MyUserDetailsServiceImpl userDetailsService) {
        this.loginProperties = securityProperties.getLoginProperties();
        this.jwtLogoutSuccessHandler = jwtLogoutSuccessHandler;
        this.userDetailsService = userDetailsService;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    @Bean
    public JWTVerifier jwtVerifier() {
        return JWT.require(algorithm)
            .withAudience(audience)
            .withIssuer(issuer)
            .build();
    }

    @Bean
    public JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler() {
        return new JwtAuthenticationFailureHandler();
    }

    @Bean
    public BearerTokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {
        BearerTokenAuthenticationFilter filter =
            new BearerTokenAuthenticationFilter(authenticationManagerBean());
        filter.setAuthenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
        filter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler());
        return filter;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtVerifier());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        this.authenticationManager = authenticationManagerBean();
        auth.userDetailsService(userDetailsService).and()
            .authenticationProvider(jwtAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        JwtWebSecurityConfigurer
            .forRS256(audience, issuer)
            .configure(httpSecurity)
            .authorizeRequests()
            // 允许对于网站静态资源的无授权访问
            .antMatchers(HttpMethod.GET,
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/*.jpg",
                "/*.png"
            ).permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // 对登录登出注册要允许匿名访问
            .antMatchers("/oauth/**", loginProperties.getLogoutUrl())
            .permitAll()
            .antMatchers("/**").authenticated()
            .and()
            .logout().logoutUrl(loginProperties.getLogoutUrl())
            .logoutSuccessHandler(jwtLogoutSuccessHandler)
            .permitAll();

        // 无权访问
        //httpSecurity.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler);
        httpSecurity
            .addFilterBefore(tokenAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers().cacheControl();
    }
}
