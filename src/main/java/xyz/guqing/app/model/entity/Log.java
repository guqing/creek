package xyz.guqing.app.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author guqing
 * @date 2020-1-11 17:47
 */
@Data
@Entity
@Table
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    private Integer type;

    private String methodName;

    private String ip;

    private Date createTime;

    private Date modifyTime;

    @ManyToOne
    private User user;
}