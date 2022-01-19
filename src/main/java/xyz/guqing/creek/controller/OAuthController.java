package xyz.guqing.creek.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.creek.config.authentication.JwtTokenProvider;
import xyz.guqing.creek.model.dto.UserInfoDTO;
import xyz.guqing.creek.model.params.LoginParam;
import xyz.guqing.creek.model.support.ResultEntity;
import xyz.guqing.creek.security.AccessToken;
import xyz.guqing.creek.security.RefreshTokenParam;
import xyz.guqing.creek.security.support.UserLoginService;
import xyz.guqing.creek.service.UserService;
import xyz.guqing.creek.utils.SecurityUserHelper;

/**
 * @author guqing
 * @date 2020-08-19
 */
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {
    private final UserLoginService userLoginService;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/token")
    public ResultEntity<AccessToken> login(@RequestBody @Valid LoginParam loginParam) {
        // 校验登录信息
        AccessToken accessToken =
            userLoginService.login(loginParam.getUsername(), loginParam.getPassword());
        return ResultEntity.ok(accessToken);
    }

    @PostMapping("/refresh")
    public ResultEntity<AccessToken> refreshToken(@RequestBody @Valid RefreshTokenParam refreshTokenParam) {
        //AccessToken accessToken = tokenProvider.refreshToken(refreshTokenParam.getToken());
        return ResultEntity.ok(null);
    }

    @GetMapping("/whoami")
    public ResultEntity<UserInfoDTO> getUserInfo() {
        String username = SecurityUserHelper.getCurrentUsername();
        return ResultEntity.ok(userService.getUserInfo(username));
    }
}
