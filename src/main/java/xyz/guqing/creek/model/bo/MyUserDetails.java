package xyz.guqing.creek.model.bo;

import java.time.LocalDateTime;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author guqing
 * @date 2020-5-13
 */
@Data
@SuppressWarnings("all")
@EqualsAndHashCode(callSuper = true)
public class MyUserDetails extends User {

    private Long id;

    /**
     * 最近访问时间
     */
    private LocalDateTime lastLoginTime;

    public MyUserDetails(String username, String password,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MyUserDetails(String username, String password, boolean enabled,
        boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
            accountNonLocked, authorities);
    }
}
