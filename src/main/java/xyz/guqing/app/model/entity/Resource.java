package xyz.guqing.app.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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

    private Boolean defaultCheck;

    private String url;

    private Date createTime;

    private Date modifyTime;
}