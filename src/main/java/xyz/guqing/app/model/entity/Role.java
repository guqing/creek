package xyz.guqing.app.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author guqing
 * @date 2019-12-22 12:41
 */
@Data
@Entity
@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    private String name;

    private String available;

    private Date createTime;

    private Date modifyTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="role")
    private Set<User> users;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="role")
    private Set<Resource> resources = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private Set<Permission> permissions;
}