package xyz.guqing.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.app.model.dto.UserDTO;
import xyz.guqing.app.model.params.LoginParam;
import xyz.guqing.app.security.support.MyUserDetails;
import xyz.guqing.app.security.support.MyUserDetailsServiceImpl;
import xyz.guqing.app.security.utils.IpUtil;
import xyz.guqing.app.security.utils.JwtTokenUtil;
import xyz.guqing.app.security.utils.SecurityUserHelper;
import xyz.guqing.app.service.UserService;
import xyz.guqing.app.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

/**
 * 用户Controller
 * @author guqing
 * @date 2019/8/11
 */
@RestController
public class UserController {
    private final MyUserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    @Autowired
    public UserController(MyUserDetailsServiceImpl userDetailsService,
                          JwtTokenUtil jwtTokenUtil,
                          UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
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

    @GetMapping("/user/info")
    public Result<UserDTO> getUserInfo(HttpServletRequest request) {
        MyUserDetails user = (MyUserDetails) SecurityUserHelper.getCurrentPrincipal();
        Integer userId = user.getId();
        UserDTO userInfo = userService.getUserInfo(userId);
        return Result.ok(userInfo);
    }
}
