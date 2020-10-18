package xyz.guqing.creek.model.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author guqing
 * @date 2020-5-13
 */
@Data
@SuppressWarnings("all")
@EqualsAndHashCode(callSuper = true)
public class MyUserDetails extends User {
    /**
     * version id
     */
    private static final long serialVersionUID = -7192149946008429854L;
    private Long id;

    /**
     * 用户组
     */
    private Long groupId;

    private String groupName;

    private List<String> roleIds;

    private List<String> roleNames;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 性别 0男 1女 2保密
     */
    private Integer gender;

    /**
     * 是否开启tab，0关闭 1开启
     */
    private Integer isTab;

    /**
     * 主题
     */
    private String theme;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 描述
     */
    private String description;

    /**
     * 最近访问时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 状态 0锁定 1有效
     */
    private Integer status;

    public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MyUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
