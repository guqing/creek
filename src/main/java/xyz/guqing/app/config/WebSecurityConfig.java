package xyz.guqing.app.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.guqing.app.security.filter.JwtTokenFilter;
import xyz.guqing.app.security.handler.MyAccessDeniedHandler;
import xyz.guqing.app.security.handler.MyAuthenticationEntryPoint;
import xyz.guqing.app.security.handler.MyLogoutSuccessHandler;
import xyz.guqing.app.security.properties.LoginProperties;
import xyz.guqing.app.security.properties.SecurityProperties;
import xyz.guqing.app.security.support.MyUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableConfigurationProperties({SecurityProperties.class})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private MyAuthenticationEntryPoint authenticationEntryPoint;
    private MyAccessDeniedHandler accessDeniedHandler;
    private MyLogoutSuccessHandler logoutSuccessHandler;
    private MyUserDetailsServiceImpl userService;
    private final LoginProperties loginProperties;

    @Autowired
    public WebSecurityConfig(SecurityProperties securityProperties,
                             MyAuthenticationEntryPoint authenticationEntryPoint,
                             MyAccessDeniedHandler accessDeniedHandler,
                             MyLogoutSuccessHandler logoutSuccessHandler,
                             MyUserDetailsServiceImpl userService) {
        this.loginProperties = securityProperties.getLoginProperties();
        // 未登陆时返回 JSON 格式的数据给前端（否则为 html）
        this.authenticationEntryPoint = authenticationEntryPoint;

        this.accessDeniedHandler = accessDeniedHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.userService = userService;
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
        auth.userDetailsService( userService );
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
                .antMatchers("/auth/**", loginProperties.getLogoutUrl())
                .permitAll()

                .anyRequest()
                // RBAC 动态 url 认证
                .access("@rbacauthorityservice.hasPermission(request,authentication)")

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

}