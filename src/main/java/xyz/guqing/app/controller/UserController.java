package xyz.guqing.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.app.model.dto.UserDTO;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.params.PasswordParam;
import xyz.guqing.app.model.params.UserParam;
import xyz.guqing.app.security.support.MyUserDetails;
import xyz.guqing.app.security.utils.SecurityUserHelper;
import xyz.guqing.app.service.UserService;
import xyz.guqing.app.utils.Result;

import javax.validation.Valid;

/**
 * 用户Controller
 *
 * @author guqing
 * @date 2019/8/11
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "UserController-用户api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    @ApiOperation(value = "获取用户信息", notes = "需要先登录获取Token才可以访问")
    public Result<UserDTO> getUserInfo() {
        Integer userId = SecurityUserHelper.getUserId();
        UserDTO userInfo = userService.getUserInfo(userId);
        return Result.ok(userInfo);
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody @Valid PasswordParam passwordParam) {
        Integer userId = SecurityUserHelper.getUserId();
        userService.updatePassword(userId, passwordParam.getOldPassword(), passwordParam.getNewPassword());
        return Result.ok();
    }

    @GetMapping("/hasInitPassword")
    public Result hasInitPassword() {
        Integer userId = SecurityUserHelper.getUserId();
        boolean hasPassword = userService.hasPassword(userId);
        return Result.ok(hasPassword);
    }

    @PostMapping("/update")
    public Result updateBaseInfo(@RequestBody @Valid UserParam userParam) {
        Integer userId = SecurityUserHelper.getUserId();
        User user = userParam.convertTo();
        userParam.setId(userId);
        userService.update(user);
        return Result.ok();
    }
}
