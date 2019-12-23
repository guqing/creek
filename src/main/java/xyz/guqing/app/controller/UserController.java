package xyz.guqing.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.app.model.dto.UserDTO;
import xyz.guqing.app.model.params.LoginParam;
import xyz.guqing.app.model.params.UserParam;
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
 * @author guqing
 * @date 2019/8/11
 */
@RestController
public class UserController {
    private final MyUserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public UserController(MyUserDetailsServiceImpl userDetailsService,
                          JwtTokenUtil jwtTokenUtil,
                          UserService userService) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public Result login(@RequestBody @Valid LoginParam loginParam, HttpServletRequest request) {
        MyUserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername(), loginParam.getLoginType());
        if(Objects.isNull(userDetails)) {
            return Result.dataNotFound("用户不存在");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordEqual = bCryptPasswordEncoder.matches(loginParam.getPassword(), userDetails.getPassword());;

        if(isPasswordEqual) {
            // 颁发token
            String token = jwtTokenUtil.generateToken(userDetails);
            // 更新用户最后登录时间和ip
            String ip = IpUtil.getIpAddr(request);
            userService.updateLoginTime(userDetails.getId(), ip);
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
