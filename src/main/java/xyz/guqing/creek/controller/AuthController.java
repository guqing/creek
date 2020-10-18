package xyz.guqing.creek.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.creek.model.dto.UserInfoDTO;
import xyz.guqing.creek.model.params.LoginParam;
import xyz.guqing.creek.model.support.ResultEntity;
import xyz.guqing.creek.security.model.AccessToken;
import xyz.guqing.creek.security.support.UserLoginService;
import xyz.guqing.creek.service.UserService;
import xyz.guqing.creek.utils.SecurityUserHelper;

import javax.validation.Valid;

/**
 * @author guqing
 * @date 2020-08-19
 */
@RestController
@RequestMapping("/authorize")
@RequiredArgsConstructor
public class AuthController {
    private final UserLoginService userLoginService;
    private final UserService userService;

    @PostMapping("/token")
    public ResultEntity<AccessToken> login(@RequestBody @Valid LoginParam loginParam) {
        // 校验登录信息
        AccessToken accessToken = userLoginService.login(loginParam.getUsername(), loginParam.getPassword());
        return ResultEntity.ok(accessToken);
    }

    @GetMapping("/user")
    public ResultEntity<UserInfoDTO> getUserInfo() {
        String username = SecurityUserHelper.getCurrentUsername();
        return ResultEntity.ok(userService.getUserInfo(username));
    }
}
