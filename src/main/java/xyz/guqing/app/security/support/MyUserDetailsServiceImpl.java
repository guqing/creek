package xyz.guqing.app.security.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.guqing.app.model.entity.Resource;
import xyz.guqing.app.model.entity.Role;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.support.LoginTypeConstant;
import xyz.guqing.app.service.PermissionService;
import xyz.guqing.app.service.RoleService;
import xyz.guqing.app.service.UserService;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * @author guqing
 * @date 2019-12-22 24:53
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    private UserService userService;
    private RoleService roleService;
    private PermissionService permissionService;

    @Autowired
    public MyUserDetailsServiceImpl(UserService userService,
                                    RoleService roleService,
                                    PermissionService permissionService) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionService = permissionService;
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
        Set<Resource> actions = role.getResources();
        userDetails.setPermissionUrl(this.getPermissionUrl(actions));


        userDetails.setRole(role);
        return userDetails;
    }
    /**
     * 根据权限列表获取权限对应的url集合
     * @param actions 权限action集合
     * @return 返回权限url
     */
    private Set<String> getPermissionUrl(Set<Resource> actions) {
        Set<String> actionUrl = new HashSet<>();
        actions.forEach(action -> {
            actionUrl.add(action.getUrl());
        });

        return actionUrl;
    }

}