package xyz.guqing.app.model.support;

/**
 * 用户状态信息静态常量<br>
 *
 * @author guqing
 * @date 2019-10-27 13:59
 */
public class UserStatusConstant {
    /**
     * 用户处于未激活状态
     */
    public static final int UNACTIVE = 0;

    /**
     * 处于正常状态
     */
    public static final int NORMAL = 1;

    /**
     * 被删除
     */
    public static final int DELETED = 2;

    /**
     * 被锁定
     */
    public static final int LOCKED = 3;
}
