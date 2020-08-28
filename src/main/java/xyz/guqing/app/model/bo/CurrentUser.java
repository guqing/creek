package xyz.guqing.app.model.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-05-21
 */
@Data
@Accessors(chain = true)
public class CurrentUser implements Serializable {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

    /**
     * 用户组
     */
    private Long groupId;

    private String groupName;

    private String roleId;

    private String roleName;
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

    private LocalDateTime createTime;
}
