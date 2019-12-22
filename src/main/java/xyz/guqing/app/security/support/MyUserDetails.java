package xyz.guqing.app.security.support;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.guqing.app.model.entity.Role;

import java.util.*;


/**
 * @author guqing
 * @date 2019-12-22 24:53
 */
@Data
public class MyUserDetails implements UserDetails {
    private Integer id;
    private String username;
    private String password;

    private Role role;
    private Set<String> permissionUrl;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if(!Objects.isNull(role) && StringUtils.isNotBlank(role.getName())) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }
    
    @Override
    public String getUsername() {
        return this.username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
