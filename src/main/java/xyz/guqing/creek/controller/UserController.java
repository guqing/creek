package xyz.guqing.creek.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.creek.exception.BadRequestException;
import xyz.guqing.creek.model.annotation.ControllerEndpoint;
import xyz.guqing.creek.model.dto.UserDTO;
import xyz.guqing.creek.model.entity.User;
import xyz.guqing.creek.model.enums.UserStatusEnum;
import xyz.guqing.creek.model.params.ChangePasswordParam;
import xyz.guqing.creek.model.params.UserParam;
import xyz.guqing.creek.model.params.UserProfileParam;
import xyz.guqing.creek.model.params.UserQuery;
import xyz.guqing.creek.model.support.*;
import xyz.guqing.creek.service.UserService;
import xyz.guqing.creek.utils.SecurityUserHelper;

import javax.validation.Valid;
import java.util.List;

/**
 * @author guqing
 * @date 2020-05-30
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:view')")
    public ResultEntity<PageInfo<UserDTO>> listUserByPage(UserQuery userQuery,
                                                          PageQuery pageQuery) {
        log.debug("用户列表查询参数: [{}]", userQuery);
        PageInfo<UserDTO> users = userService.listByPage(userQuery, pageQuery);
        return ResultEntity.ok(users);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:add')")
    @ControllerEndpoint(operation = "新增用户", exceptionMessage = "新增用户失败")
    public ResultEntity<String> addUser(@RequestBody @Validated(CreateCheck.class) UserParam userParam) {
        userService.createUser(userParam);
        return ResultEntity.ok();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    @ControllerEndpoint(operation = "修改用户", exceptionMessage = "修改用户失败")
    public ResultEntity<String> updateUser(@RequestBody @Validated(UpdateCheck.class) UserParam userParam) {
        userService.updateUser(userParam);
        return ResultEntity.ok();
    }

    @PutMapping("profile")
    @ControllerEndpoint(operation="修改个人信息", exceptionMessage = "修改个人信息失败")
    public ResultEntity<String> updateProfile(@RequestBody @Valid UserProfileParam userParam) {
        String username = SecurityUserHelper.getCurrentUsername();
        if(!SecurityUserHelper.isCurrentUser(username)) {
            throw new AccessDeniedException("无权修改别人的信息");
        }
        // 根据用户名查询
        User user = userService.getByUsername(username);
        // 使用参数更新 user
        userParam.update(user);
        // 更新
        userService.updateById(user);
        return ResultEntity.ok();
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
    public ResultEntity<String> updatePassword(@RequestBody ChangePasswordParam passwordParam) {
        String username = SecurityUserHelper.getCurrentUsername();
        userService.updatePassword(username, passwordParam.getOldPassword(), passwordParam.getNewPassword());
        return ResultEntity.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('user:delete')")
    @ControllerEndpoint(operation = "删除用户", exceptionMessage = "删除用户失败")
    public ResultEntity<String> deleteUsers(@RequestBody List<String> usernameList) {
        // 使用逻辑删除
        userService.removeByUserNames(usernameList);
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
            throw new BadRequestException("无法锁定当前登录帐号");
        }

        userService.updateStatus(username, UserStatusEnum.DISABLE);
        return ResultEntity.ok();
    }

    @PutMapping("/unlock/{username}")
    @PreAuthorize("hasAuthority('user:update')")
    @ControllerEndpoint(operation = "解锁用户帐号", exceptionMessage = "解锁用户帐号失败")
    public ResultEntity<String> unlockUser(@PathVariable String username) {
        userService.updateStatus(username, UserStatusEnum.NORMAL);
        return ResultEntity.ok();
    }
}
