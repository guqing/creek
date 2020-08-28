package xyz.guqing.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.app.model.bo.CurrentUser;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.enums.UserStatusEnum;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 如果查询到返回用户信息，否则抛出NotFoundException
     */
    CurrentUser loadUserByUsername(String username);

    /**
     * 修改用户头像
     * @param username 用户名
     * @param avatar 头像url
     */
    void updateAvatar(String username, String avatar);

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return 用户名已经存在返回{@code true},否则返回{@code false}
     */
    boolean isPresentByUsername(String username);

    /**
     * 判断邮箱是否已经被绑定
     * @param email 邮箱地址
     * @return 如果邮箱地址已经被绑定则返回{@code true},否则返回{@code false}
     */
    boolean isPresentByEmail(String email);

    /**
     * 判断用户密码是否正确
     * @param username 用户名
     * @param password 密码
     * @return 如果正确返回{@code true},否则返回{@code false}
     */
    boolean isCorrectByPassword(@NotNull String username, @NotNull String password);

    /**
     * 重置用户密码
     * @param username 用户名
     */
    void resetPassword(@NotNull String username);

    /**
     * 更新用户状态
     * @param username 用户名
     * @param status 用户状态
     */
    void updateStatus(@NotNull String username, UserStatusEnum status);

    /**
     * 根据用户名集合批量删除用户
     * @param userNames 用户名集合
     */
    void removeByUserNames(List<String> userNames);

    /**
     * 修改用户密码
     * @param username 用户名
     * @param oldPassword 原始密码
     * @param newPassword 新密码
     */
    void updatePassword(String username, String oldPassword, String newPassword);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 返回用户信息
     */
    Optional<User> getByUsername(String username);

    /**
     * 根据邮箱地址查询用户
     * @param email 邮箱地址
     * @return 返回用户optional
     */
    Optional<User> getByEmail(String email);
}
