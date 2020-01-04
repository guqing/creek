package xyz.guqing.app.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

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

    private Integer available;

    private Date createTime;

    private Date modifyTime;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Permission> permissions;
}