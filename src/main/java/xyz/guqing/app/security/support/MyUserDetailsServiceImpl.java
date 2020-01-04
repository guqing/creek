package xyz.guqing.app.security.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.guqing.app.model.entity.Permission;
import xyz.guqing.app.model.entity.Resource;
import xyz.guqing.app.model.entity.Role;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.support.LoginTypeConstant;
import xyz.guqing.app.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * @author guqing
 * @date 2019-12-22 24:53
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    private UserService userService;

    @Autowired
    public MyUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUser(username, LoginTypeConstant.USERNAME);
    }

    public MyUserDetails loadUserByUsername(String username, Integer loginType) throws UsernameNotFoundException {
        return loadUser(username, loginType);
    }

    private MyUserDetails loadUser(String username, Integer loginType) {
        MyUserDetails userDetails = new MyUserDetails();
        User user = userService.getUserByUsername(username, loginType);

        if(Objects.isNull(user)) {
            return null;
        }

        userDetails.setId(user.getId());
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        Role role = user.getRole();

        // 设置权限url
        List<Permission> permissions = role.getPermissions();

        userDetails.setPermissionUrl(this.getActionUrl(permissions));


        userDetails.setRole(role);
        return userDetails;
    }
    /**
     * 根据权限列表获取权限对应的url集合
     * @param permissions 权限菜单
     * @return 返回权限url
     */
    private Set<String> getActionUrl(List<Permission> permissions) {
        Set<String> actionUrl = new HashSet<>();
        permissions.forEach(permission -> {
            List<Resource> resources = permission.getResources();
            resources.forEach(resource -> {
                actionUrl.add(resource.getUrl());
            });
        });

        return actionUrl;
    }

}