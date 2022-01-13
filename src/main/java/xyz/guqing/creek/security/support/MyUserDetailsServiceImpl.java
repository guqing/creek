package xyz.guqing.creek.security.support;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.guqing.creek.model.bo.CurrentUser;
import xyz.guqing.creek.service.RoleResourceService;
import xyz.guqing.creek.service.UserService;
import xyz.guqing.creek.utils.CreekUtils;

/**
 * @author guqing
 * @date 2019-12-22 24:53
 */
@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final RoleResourceService roleResourceService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        CurrentUser user = userService.loadUserByUsername(username);
        String password = user.getPassword();

        List<Long> roleIds = CreekUtils.splitToLong(user.getRoleIds());
        List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(roleIds);
        User myUserDetails = new User(
            username,
            password,
            true,
            true,
            true,
            true,
            grantedAuthorities
        );

        BeanUtils.copyProperties(user, myUserDetails);

        // 返回自定义的 MyUserDetails
        return myUserDetails;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<Long> roleIds) {
        return roleResourceService.listScopeNameByRoleIds(roleIds).stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }
}
