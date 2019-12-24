package xyz.guqing.app.controller;

import cn.hutool.json.JSONUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.app.exception.UnsupportedOauthTypeException;
import xyz.guqing.app.model.params.LoginParam;
import xyz.guqing.app.security.support.MyUserDetails;
import xyz.guqing.app.security.support.MyUserDetailsServiceImpl;
import xyz.guqing.app.security.utils.IpUtil;
import xyz.guqing.app.security.utils.JwtTokenUtil;
import xyz.guqing.app.service.UserService;
import xyz.guqing.app.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

/**
 * 用户登录认证Controller
 * 包括本地登录和社交登录
 * @author guqing
 * @date 2019-12-24 15:58
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class OauthController {
    private final AuthRequestFactory factory;
    private final MyUserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    @Autowired
    public OauthController(MyUserDetailsServiceImpl userDetailsService,
                          AuthRequestFactory factory,
                          JwtTokenUtil jwtTokenUtil,
                          UserService userService) {
        this.userDetailsService = userDetailsService;
        this.factory = factory;
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody @Valid LoginParam loginParam, HttpServletRequest request) {
        MyUserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername(), loginParam.getLoginType());

        if(Objects.isNull(userDetails)) {
            return Result.dataNotFound("用户不存在");
        }

        String ip = IpUtil.getIpAddr(request);
        // 校验登录信息
        String token = userService.login(loginParam, userDetails, ip);

        if(token != null) {
            return Result.ok(token);
        }

        return Result.loginFail();
    }

    @GetMapping("/login/{type}")
    public void renderAuth(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest(type);
        // 生成授权地址
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/{type}/callback")
    public Result login(@PathVariable String type, AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest(type);
        // 登录后获取到响应信息
        AuthResponse response = authRequest.login(callback);
        log.info("【response】= {}", JSONUtil.toJsonStr(response));

        return Result.ok();
    }

    private AuthRequest getAuthRequest(String type) {
        try {
            // 此处可能获取到不支持的登录类型，全局异常处理
            return factory.get(type);
        } catch (Exception e) {
            throw new UnsupportedOauthTypeException("不支持此第三方登录类型");
        }
    }
}
