package xyz.guqing.app.model.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 社交登录关联
 * @author guqing
 * @date 2019-12-24 21:49
 */
@Data
@Entity
@Table
public class UserConnect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 第三方登录提供商例如QQ,GITHUB
     */
    private String providerId;
    private String accessToken;
    private String tokenType;
    private String openId;
    private String uuid;
    private Integer expireIn;
    @ManyToOne
    private User user;
}
