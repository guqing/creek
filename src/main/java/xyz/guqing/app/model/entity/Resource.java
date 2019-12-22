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
@Table(name="resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String action;

    private String description;

    private Boolean defaultcheck;

    private String url;

    private Date createTime;

    private Date modifyTime;

    @ManyToOne
    private Permission permission;

    @ManyToOne
    private Role role;
}