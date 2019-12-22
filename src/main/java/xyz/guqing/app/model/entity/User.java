package xyz.guqing.app.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private Integer gender;

    private Date birthday;

    private String mobile;

    private String email;

    private String avatar;

    private String description;

    private Integer status;

    private Integer userLevel;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Date createTime;

    private Date modifyTime;

    private Integer deleted;

    @ManyToOne(fetch=FetchType.EAGER)
    private Role role;
}