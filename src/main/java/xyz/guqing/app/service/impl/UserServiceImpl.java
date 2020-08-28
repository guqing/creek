package xyz.guqing.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.guqing.app.exception.BadArgumentException;
import xyz.guqing.app.exception.NotFoundException;
import xyz.guqing.app.mapper.UserMapper;
import xyz.guqing.app.mapper.UserRoleMapper;
import xyz.guqing.app.model.bo.CurrentUser;
import xyz.guqing.app.model.constant.CreekConstant;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.enums.UserStatusEnum;
import xyz.guqing.app.service.RoleService;
import xyz.guqing.app.service.UserService;

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
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final RoleService roleService;
    private final UserRoleMapper userRoleMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public CurrentUser loadUserByUsername(String username) {
        Optional<CurrentUser> userOptional = baseMapper.findByUsername(username);
        return userOptional.orElseThrow(() -> new NotFoundException("用户不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String username, String avatar) {
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate(User.class);
        updateWrapper.set(User::getAvatar, avatar)
                .eq(User::getUsername, username);
        update(updateWrapper);
    }

    @Override
    public boolean isPresentByUsername(String username) {
        Optional<User> userOptional = getByUsername(username);
        // 不为空即存在返回true
        return userOptional.isPresent();
    }

    @Override
    public boolean isPresentByEmail(String email) {
        Optional<User> userOptional = getByEmail(email);
        return userOptional.isPresent();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUsername, username);
        return Optional.ofNullable(getOne(queryWrapper));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getEmail, email);
        return Optional.ofNullable(getOne(queryWrapper));
    }

    @Override
    public boolean isCorrectByPassword(String username, String password) {
        Optional<User> userOptional = getByUsername(username);
        if(!userOptional.isPresent()) {
            throw new NotFoundException("用户不存在");
        }
        User user = userOptional.get();
        // 加密后匹配
        String encodedPassword = passwordEncoder.encode(password);
        return passwordEncoder.matches(encodedPassword, user.getPassword());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String username) {
        Optional<User> userOptional = getByUsername(username);
        if(!userOptional.isPresent()) {
            throw new NotFoundException("用户不存在");
        }
        User user = userOptional.get();
        String defaultPassword = passwordEncoder.encode(CreekConstant.DEFAULT_PASSWORD);
        user.setPassword(defaultPassword);

        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String username, UserStatusEnum status) {
        Optional<User> userOptional = getByUsername(username);
        if(!userOptional.isPresent()) {
            throw new NotFoundException("用户不存在");
        }
        User user = userOptional.get();
        user.setStatus(status.getValue());
        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByUserNames(List<String> usernames) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(User::getUsername, usernames);
        remove(queryWrapper);
    }

    @Override
    public void updatePassword(String username, String oldPassword, String newPassword) {
        boolean correctByPassword = isCorrectByPassword(username, oldPassword);
        if(!correctByPassword) {
            throw new BadArgumentException("原始密码不正确");
        }
        // 修改密码
        String encodedPassword = passwordEncoder.encode(newPassword);
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(User::getPassword, encodedPassword)
                .eq(User::getUsername, username);
        update(updateWrapper);
    }
}
