package xyz.guqing.creek.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import xyz.guqing.creek.model.bo.CurrentUser;
import xyz.guqing.creek.model.dto.UserDTO;
import xyz.guqing.creek.model.dto.UserInfoDTO;
import xyz.guqing.creek.model.entity.User;
import xyz.guqing.creek.model.enums.UserStatusEnum;
import xyz.guqing.creek.model.params.UserParam;
import xyz.guqing.creek.model.params.UserQuery;
import xyz.guqing.creek.model.support.PageInfo;
import xyz.guqing.creek.model.support.PageQuery;

import java.time.LocalDateTime;
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
     * 分页查询用户列表
     * @param userQuery 查询条件
     * @param pageQuery 分页
     * @return 返回分页查询结果
     */
    PageInfo<UserDTO> listByPage(UserQuery userQuery, PageQuery pageQuery);

    /**
     * 添加用户
     * @param userParam 用户参数
     */
    void createUser(UserParam userParam);

    /**
     * 根据id用户id更新用户信息和用户角色关系
     * @param userParam 用户信息
     */
    void updateUser(UserParam userParam);

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
    boolean isCorrectByPassword(@NonNull String username, @NonNull String password);

    /**
     * 重置用户密码
     * @param username 用户名
     */
    void resetPassword(@NonNull String username);

    /**
     * 更新用户状态
     * @param username 用户名
     * @param status 用户状态
     */
    void updateStatus(@NonNull String username, UserStatusEnum status);

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
     * 根据用户名查询用户
     * @param username 用户名
     * @return 如果查询到返回用户信息,查询不到返回null
     */
    User getByUsername(String username);

    /**
     * 根据邮箱地址查询用户
     * @param email 邮箱
     * @return 返回查询到的用户信息Optional,可为空
     */
    Optional<User> getByEmail(String email);

    /**
     * 查询用户信息
     * @param username 用户名
     * @return 返回查询到的用户信息dto，用户不存在抛出异常
     */
    UserInfoDTO getUserInfo(String username);

    /**
     * 更新最后登录时间
     * @param username 用户名
     * @param loginTime 最后登录时间
     */
    void updateLastLoginTime(String username, LocalDateTime loginTime);
}
