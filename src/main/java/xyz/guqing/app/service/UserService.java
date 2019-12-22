package xyz.guqing.app.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.guqing.app.model.dto.PermissionDTO;
import xyz.guqing.app.model.dto.RoleDTO;
import xyz.guqing.app.model.dto.UserDTO;
import xyz.guqing.app.model.entity.Permission;
import xyz.guqing.app.model.entity.Role;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.support.LoginTypeConstant;
import xyz.guqing.app.model.support.UserStatusConstant;
import xyz.guqing.app.repository.UserRepository;

import java.util.*;
import java.util.Optional;

/**
 * @author guqing
 * @date 2019/8/9
 */
@Service
@CacheConfig(cacheNames = "userService")
public class UserService {
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PermissionService permissionService) {
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    @Cacheable(key = "#username", unless = "#result==null")
    public User getUserByUsername(String username, Integer loginType){
        if(loginType == LoginTypeConstant.EMAIL) {
            return userRepository.findByUsername(username, UserStatusConstant.NORMAL, UserStatusConstant.NORMAL);
        } else if(loginType == LoginTypeConstant.USERNAME){
            return userRepository.findByEmail(username, UserStatusConstant.NORMAL, UserStatusConstant.NORMAL);
        }

        return null;
    }

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return 用户信息DTO对象
     */
    @Cacheable(unless = "#result==null")
	public UserDTO getUserInfo(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Assert.isTrue(userOptional.isPresent(), "用户不存在");
        User user = userOptional.get();
        Role role = user.getRole();
        Set<Permission> permissions = permissionService.findByRoleId(role.getId());
        return userDtoConverter(user, role, permissions);
    }

    /**
     * 更新用户的最后登录时间
     * @param userId 用户id
     * @param ip 用户登录ip
     */
    public void updateLoginTime(Integer userId, String ip) {
        Optional<User> userOptional = userRepository.findById(userId);
        Assert.isTrue(userOptional.isPresent(), "用户不存在");
        User user = userOptional.get();
        user.setId(userId);
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(ip);
        userRepository.save(user);
    }

    private UserDTO userDtoConverter(User user, Role role, Set<Permission> permissions) {
        UserDTO userDTO = new UserDTO().convertFrom(user);
        //设置角色和权限
        RoleDTO roleDTO = new RoleDTO().convertFrom(role);
        userDTO.setRole(roleDTO);
        userDTO.setRoleId(roleDTO.getName());

        List<PermissionDTO> permissionDtoList = new ArrayList<>();
        permissions.forEach(permission -> {
            PermissionDTO permissionDTO = new PermissionDTO().convertFrom(permission);
            permissionDtoList.add(permissionDTO);
        });

        roleDTO.setPermissions(permissionDtoList);
        return userDTO;
    }
}
