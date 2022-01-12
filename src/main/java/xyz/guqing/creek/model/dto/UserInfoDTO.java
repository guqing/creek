package xyz.guqing.creek.model.dto;

import java.util.Set;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guqing
 * @date 2020-05-29
 */
@Data
public class UserInfoDTO {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    private String nickname;

    /**
     * 用户组
     */
    private Long groupId;

    private String groupName;

    private List<Long> roleIds;

    private List<String> roleNames;

    private Set<String> scopes;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String mobile;

    private String gender;

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

    private LocalDateTime createTime;
}
