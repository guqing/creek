package xyz.guqing.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.app.model.params.LoginParam;
import xyz.guqing.app.model.support.ResultEntity;
import xyz.guqing.app.security.model.AccessToken;
import xyz.guqing.app.security.support.UserLoginService;

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

    @PostMapping("/token")
    public ResultEntity<AccessToken> login(@RequestBody @Valid LoginParam loginParam) {
        // 校验登录信息
        AccessToken accessToken = userLoginService.login(loginParam.getUsername(), loginParam.getPassword());
        return ResultEntity.ok(accessToken);
    }
}
