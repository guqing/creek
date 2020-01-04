package xyz.guqing.app.model.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author guqing
 * @date 2019-12-22 12:41
 */
@Data
@Entity
@Table
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String identify;

    private String name;

    private String description;

    private String sortIndex;

    private String available;

    private Date createTime;

    private Date modifyTime;

    /**所属资源*/
    @OneToMany(fetch = FetchType.EAGER)
    private List<Resource> resources;
}