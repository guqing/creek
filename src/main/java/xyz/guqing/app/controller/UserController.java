package xyz.guqing.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.app.model.annotation.ControllerEndpoint;
import xyz.guqing.app.model.enums.UserStatusEnum;
import xyz.guqing.app.model.support.ResultEntity;
import xyz.guqing.app.service.UserService;
import xyz.guqing.app.utils.SecurityUserHelper;

/**
 * @author guqing
 * @date 2020-05-30
 */
@Slf4j
@RestController
@RequestMapping("/ums/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("avatar")
    @ControllerEndpoint(exceptionMessage = "修改头像失败")
    public ResultEntity<String> updateAvatar(@RequestParam String avatar) {
        String username = SecurityUserHelper.getCurrentUsername();
        userService.updateAvatar(username, avatar);
        return ResultEntity.ok();
    }

    @PutMapping("password")
    @ControllerEndpoint(exceptionMessage = "修改密码")
    public ResultEntity<String> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        String username = SecurityUserHelper.getCurrentUsername();
        userService.updatePassword(username, oldPassword, newPassword);
        return ResultEntity.ok();
    }

    @GetMapping("/check/username")
    public ResultEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean isPresent = userService.isPresentByUsername(username);
        return ResultEntity.ok(isPresent);
    }

    @GetMapping("/check/email")
    public ResultEntity<Boolean> checkEmail(String email) {
        boolean isPresent = userService.isPresentByEmail(email);
        return ResultEntity.ok(isPresent);
    }

    @GetMapping("/check/password")
    public ResultEntity<Boolean> checkPassword(@RequestParam String password) {
        String username = SecurityUserHelper.getCurrentUsername();
        boolean isCorrect = userService.isCorrectByPassword(username, password);
        return ResultEntity.ok(isCorrect);
    }

    @PutMapping("/reset/{username}")
    @PreAuthorize("hasAuthority('user:reset')")
    @ControllerEndpoint(operation = "重置用户密码", exceptionMessage = "重置用户密码失败")
    public ResultEntity<String> resetPassword(@PathVariable String username) {
        userService.resetPassword(username);
        return ResultEntity.ok();
    }

    @PutMapping("/lock/{username}")
    @PreAuthorize("hasAuthority('user:update')")
    @ControllerEndpoint(operation = "锁定用户帐号", exceptionMessage = "锁定用户帐号失败")
    public ResultEntity<String> lockUser(@PathVariable String username) {
        if(username.equals(SecurityUserHelper.getCurrentUsername())) {
            return ResultEntity.accessDenied("无法锁定自己的账号");
        }

        userService.updateStatus(username, UserStatusEnum.DISABLE);
        return ResultEntity.ok();
    }

    @PutMapping("/unlock/{username}")
    @PreAuthorize("hasAuthority('user:update')")
    @ControllerEndpoint(operation = "解锁用户帐号", exceptionMessage = "解锁用户帐号失败")
    public ResultEntity<String> unlockUser(@PathVariable String username) {
        if(username.equals(SecurityUserHelper.getCurrentUsername())) {
            return ResultEntity.accessDenied("无法解锁自己的账号");
        }

        userService.updateStatus(username, UserStatusEnum.NORMAL);
        return ResultEntity.ok();
    }
}
