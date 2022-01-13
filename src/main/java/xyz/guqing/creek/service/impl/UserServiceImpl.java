package xyz.guqing.creek.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.guqing.creek.exception.BadArgumentException;
import xyz.guqing.creek.exception.NotFoundException;
import xyz.guqing.creek.mapper.UserGroupMapper;
import xyz.guqing.creek.mapper.UserMapper;
import xyz.guqing.creek.model.bo.CurrentUser;
import xyz.guqing.creek.model.constant.CreekConstant;
import xyz.guqing.creek.model.dos.UserDO;
import xyz.guqing.creek.model.dto.UserDTO;
import xyz.guqing.creek.model.dto.UserInfoDTO;
import xyz.guqing.creek.model.entity.Role;
import xyz.guqing.creek.model.entity.User;
import xyz.guqing.creek.model.entity.UserGroup;
import xyz.guqing.creek.model.enums.GenderEnum;
import xyz.guqing.creek.model.enums.UserStatusEnum;
import xyz.guqing.creek.model.params.UserParam;
import xyz.guqing.creek.model.params.UserQuery;
import xyz.guqing.creek.model.support.PageInfo;
import xyz.guqing.creek.model.support.PageQuery;
import xyz.guqing.creek.service.RoleService;
import xyz.guqing.creek.service.UserService;
import xyz.guqing.creek.utils.CreekUtils;
import xyz.guqing.creek.utils.PageUtils;
import xyz.guqing.creek.utils.ServiceUtils;

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
    private final PasswordEncoder passwordEncoder;
    private final UserGroupMapper userGroupMapper;

    @Override
    public PageInfo<UserDTO> listByPage(UserQuery userQuery, PageQuery pageQuery) {
        // 查询
        Page<UserDO> userPage = this.baseMapper.findUserBy(PageUtils.convert(pageQuery), userQuery);
        List<UserDTO> userDtoList = ServiceUtils.convertToList(userPage.getRecords(), user -> {
            UserDTO userDTO = new UserDTO().convertFrom(user);
            List<String> roleIds = CreekUtils.commaSeparatedToList(user.getRoleIds());
            userDTO.setRoleIds(roleIds);
            List<String> roleNames = roleService.listByIds(roleIds).stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());
            userDTO.setRoleNames(roleNames);
            return userDTO;
        });
        return PageInfo.convertFrom(userPage, userDtoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(UserParam userParam) {
        User user = userParam.convertTo();
        user.setNickname(user.getUsername());
        user.setStatus(UserStatusEnum.NORMAL.getValue());
        user.setGender(GenderEnum.MALE.name());
        user.setRoleIds(StringUtils.join(userParam.getRoleIds(), ","));
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //保存用户信息
        save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserParam userParam) {
        User user = userParam.convertTo();
        user.setRoleIds(StringUtils.join(userParam.getRoleIds(), ","));
        // 加密密码
        String password = user.getPassword();
        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }

        this.updateById(user);
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
        // 不为空即存在返回true
        return Objects.nonNull(getByUsername(username));
    }

    @Override
    public boolean isPresentByEmail(String email) {
        Optional<User> userOptional = getByEmail(email);
        return userOptional.isPresent();
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUsername, username);
        return getOne(queryWrapper);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getEmail, email);
        return Optional.ofNullable(getOne(queryWrapper));
    }

    @Override
    public UserInfoDTO getUserInfo(String username) {
        CurrentUser currentUser = loadUserByUsername(username);
        UserInfoDTO userInfoDTO = convertTo(currentUser);
        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        Set<String> scopes = authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet());
        userInfoDTO.setScopes(scopes);

        UserGroup userGroup = userGroupMapper.selectById(currentUser.getGroupId());
        if (userGroup != null) {
            userInfoDTO.setGroupName(userGroup.getGroupName());
        }
        return userInfoDTO;
    }

    @Override
    public void updateLastLoginTime(String username, LocalDateTime loginTime) {
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(User::getLastLoginTime, loginTime).eq(User::getUsername, username);
        update(updateWrapper);
    }

    private UserInfoDTO convertTo(CurrentUser currentUser) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(currentUser, userInfoDTO);
        List<Long> roleIds = new ArrayList<>();
        List<String> roleNames = new ArrayList<>();
        for (Role role : currentUser.getRoles()) {
            roleIds.add(role.getId());
            roleNames.add(role.getRoleName());
        }
        userInfoDTO.setRoleIds(roleIds);
        userInfoDTO.setRoleNames(roleNames);
        return userInfoDTO;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) {
        Optional<CurrentUser> userOptional = baseMapper.findByUsername(username);
        return userOptional.map(user -> {
                List<String> roleIds = CreekUtils.commaSeparatedToList(user.getRoleIds());
                user.setRoles(roleService.listByIds(roleIds));
                return user;
            })
            .orElseThrow(() -> new NotFoundException("用户不存在"));
    }

    @Override
    public boolean isCorrectByPassword(String username, String password) {
        User user = getByUsername(username);
        // 直接匹配，无需先加密
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String username) {
        User user = getByUsername(username);
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }
        String defaultPassword = passwordEncoder.encode(CreekConstant.DEFAULT_PASSWORD);
        user.setPassword(defaultPassword);

        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String username, UserStatusEnum status) {
        User user = getByUsername(username);
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }
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
        if (!correctByPassword) {
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
