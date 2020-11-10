package xyz.guqing.creek.config;

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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.guqing.creek.security.filter.JwtTokenFilter;
import xyz.guqing.creek.security.handler.MyAccessDeniedHandler;
import xyz.guqing.creek.security.handler.MyAuthenticationEntryPoint;
import xyz.guqing.creek.security.handler.MyLogoutSuccessHandler;
import xyz.guqing.creek.security.properties.LoginProperties;
import xyz.guqing.creek.security.properties.SecurityProperties;
import xyz.guqing.creek.security.support.MyUserDetailsServiceImpl;

/**
 * @author guqing
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableConfigurationProperties({SecurityProperties.class})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final MyAuthenticationEntryPoint authenticationEntryPoint;
    private final MyAccessDeniedHandler accessDeniedHandler;
    private final MyLogoutSuccessHandler logoutSuccessHandler;
    private final MyUserDetailsServiceImpl userDetailsService;
    private final LoginProperties loginProperties;

    private AuthenticationManager authenticationManager;

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public WebSecurityConfig(SecurityProperties securityProperties,
                             MyAuthenticationEntryPoint authenticationEntryPoint,
                             MyAccessDeniedHandler accessDeniedHandler,
                             MyLogoutSuccessHandler logoutSuccessHandler,
                             MyUserDetailsServiceImpl userDetailsService) {
        this.loginProperties = securityProperties.getLoginProperties();
        // 未登陆时返回 JSON 格式的数据给前端（否则为 html）
        this.authenticationEntryPoint = authenticationEntryPoint;

        this.accessDeniedHandler = accessDeniedHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtTokenFilter();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        this.authenticationManager = authenticationManagerBean();
        auth.userDetailsService( userDetailsService );
    }

    @Override
    protected void configure( HttpSecurity httpSecurity ) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                // 使用 JWT，关闭token
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)

                .and()
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

                // 对登录登出注册要允许匿名访问
                .antMatchers("/authorize/**", loginProperties.getLogoutUrl())
                .permitAll()
                .and()
                .logout().logoutUrl(loginProperties.getLogoutUrl())
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();


        // 无权访问
        httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers().cacheControl();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }
}