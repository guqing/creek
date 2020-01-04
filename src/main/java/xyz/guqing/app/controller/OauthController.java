package xyz.guqing.app.controller;

import cn.hutool.json.JSONUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
import xyz.guqing.app.utils.IpUtils;
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
@Api(value = "OauthController-用户登录api")
public class OauthController {
    /**
     * 第三方登录成功后的返回code，如果不是此值则说明登录失败
     */
    private static final Integer JUST_AUTH_SUCCESS_CODE = 2000;

    private final AuthRequestFactory factory;
    private final MyUserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    @Autowired
    public OauthController(MyUserDetailsServiceImpl userDetailsService,
                          AuthRequestFactory factory,
                          UserService userService) {
        this.userDetailsService = userDetailsService;
        this.factory = factory;
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiOperation(value="使用系统账号登录", notes="支持用户名、邮箱地址、手机号码登录")
    public Result login(@RequestBody @Valid LoginParam loginParam, HttpServletRequest request) {
        log.info("登录用户参数：{}", loginParam);
        MyUserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername(), loginParam.getLoginType());

        if(Objects.isNull(userDetails)) {
            return Result.dataNotFound("用户不存在");
        }

        String ip = IpUtil.getIpAddr(request);
        // 校验登录信息
        String token = userService.login(loginParam, userDetails, ip);

        return Result.ok(token);
    }

    @GetMapping("/login/{type}")
    @ApiOperation(value="前往第三方登录页", notes="支持的第三方平台参考：https://github.com/justauth/JustAuth")
    public void renderAuth(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest(type);
        // 生成授权地址
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        response.sendRedirect(authorizeUrl);
    }

    @RequestMapping("/{type}/callback")
    @ApiOperation(value="第三方登录授权回调", notes="登录成功后的回调地址，系统会根据回调生成token")
    @ApiImplicitParam(paramType="path", name = "type", value = "登录类型，例如qq,weibo,github", required = true, dataType = "String")
    public Result login(@PathVariable String type, AuthCallback callback, HttpServletRequest request) {
        AuthRequest authRequest = getAuthRequest(type);
        // 登录后获取到响应信息
        AuthResponse response = authRequest.login(callback);
        if(!JUST_AUTH_SUCCESS_CODE.equals(response.getCode())) {
            return Result.loginFail();
        }
        String ipAddr = IpUtils.getIpAddr(request);
        log.info("【response】= {}", JSONUtil.toJsonStr(response));
        String token = userService.oauthLogin(response, ipAddr);
        return Result.ok(token);
    }

    @GetMapping("/test")
    public String hello() {
        return "测试跨域";
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
