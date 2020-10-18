package xyz.guqing.creek.controller;

import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.guqing.creek.model.constant.StringConstant;
import xyz.guqing.creek.model.dto.SocialLoginDTO;
import xyz.guqing.creek.model.params.BindUserParam;
import xyz.guqing.creek.model.params.SocialUserParam;
import xyz.guqing.creek.model.support.ResultEntity;
import xyz.guqing.creek.security.model.AccessToken;
import xyz.guqing.creek.security.properties.LoginProperties;
import xyz.guqing.creek.security.support.UserLoginService;
import xyz.guqing.creek.utils.SecurityUserHelper;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("social")
public class SocialLoginController {
    private static final String TYPE_BIND = "bind";
    private final UserLoginService userLoginService;
    private final LoginProperties loginProperties;
    private final AuthRequestFactory authRequestFactory;

    @ResponseBody
    @GetMapping("/list")
    public ResultEntity<List<String>> connections() {
        List<String> oauthList = authRequestFactory.oauthList();
        return ResultEntity.ok(oauthList);
    }

    @GetMapping("/login/{oauthType}/{type}")
    public void login(@PathVariable String oauthType,
                      @PathVariable String type,
                      HttpServletResponse response) throws IOException {
        AuthRequest authRequest = userLoginService.getAuthRequest(oauthType);
        String state = AuthStateUtils.createState() + StringConstant.DOUBLE_COLON + type;
        response.sendRedirect(authRequest.authorize(state));
    }

    @RequestMapping("/{oauthType}/callback")
    public ModelAndView login(@PathVariable String oauthType,
                              String state,
                              AuthCallback callback,
                              ModelAndView modelAndView) {
        String type = StringUtils.substringAfterLast(state, StringConstant.DOUBLE_COLON);

        SocialLoginDTO socialLoginDTO;
        if (StringUtils.equals(type, TYPE_BIND)) {
            // 帐号绑定
            socialLoginDTO = userLoginService.resolveBind(oauthType, callback);
        } else {
            // 登录
            socialLoginDTO = userLoginService.resolveLogin(oauthType, callback);
        }

        modelAndView.addObject("response", socialLoginDTO);
        modelAndView.addObject("redirectUrl", loginProperties.getSocialRedirectUrl());
        modelAndView.setViewName("socialLoginResult");
        return modelAndView;
    }

    /**
     * 注册并登录
     *
     * @param registerUser register user
     * @return ResultEntity result entity
     */
    @ResponseBody
    @PostMapping("sign/login")
    public ResultEntity<AccessToken> signLogin(@RequestBody @Valid BindUserParam registerUser) {
        AccessToken accessToken = this.userLoginService.socialSignLogin(registerUser);
        return ResultEntity.ok(accessToken);
    }

    /**
     * 绑定社交帐号
     * @param socialUserParam 第三方用户信息
     */
    @ResponseBody
    @PostMapping("/bind")
    public ResultEntity<String> bind(@RequestBody SocialUserParam socialUserParam) {
        String username = SecurityUserHelper.getCurrentUsername();
        AuthUser authUser = socialUserParam.convertTo();
        this.userLoginService.bind(username, authUser);
        return ResultEntity.ok();
    }

    @ResponseBody
    @PostMapping("/unbind/{oauthType}")
    public ResultEntity<String> unbind(@PathVariable String oauthType) {
        String username = SecurityUserHelper.getCurrentUsername();
        this.userLoginService.unbind(username, oauthType);
        return ResultEntity.ok();
    }

    @ResponseBody
    @GetMapping("providers")
    public ResultEntity<List<String>> listUserConnections() {
        String username = SecurityUserHelper.getCurrentUsername();
        List<String> providerNames = userLoginService.listProviderByUsername(username);
        return ResultEntity.ok(providerNames);
    }
}
