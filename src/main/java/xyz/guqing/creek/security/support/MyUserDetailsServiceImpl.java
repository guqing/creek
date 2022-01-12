package xyz.guqing.creek.security.support;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.guqing.creek.model.bo.CurrentUser;
import xyz.guqing.creek.model.entity.ApiScope;
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
        String scopes = roleResourceService.listScopesByIds(roleIds)
            .stream()
            .map(ApiScope::getName)
            .collect(Collectors.joining(","));
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.NO_AUTHORITIES;
        if (StringUtils.isNotBlank(scopes)) {
            grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(scopes);
        }
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
}
