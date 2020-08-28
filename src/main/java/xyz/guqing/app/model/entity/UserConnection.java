package xyz.guqing.app.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户社交账户关联表
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Data
@Accessors(chain = true)
public class UserConnection {

    private static final long serialVersionUID = 1L;

    /**
     * Violet系统用户id
     */
    private Long userId;

    /**
     * 第三方平台名称
     */
    private String providerName;

    /**
     * 第三方平台账户ID
     */
    private String providerUserId;

    /**
     * 第三方平台用户名
     */
    private String providerUserName;

    /**
     * 第三方平台昵称
     */
    private String nickName;

    /**
     * 第三方平台头像
     */
    private String avatar;

    /**
     * 地址
     */
    private String location;

    /**
     * 备注
     */
    private String remark;
}
