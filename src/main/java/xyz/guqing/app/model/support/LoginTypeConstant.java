package xyz.guqing.app.model.support;

/**
 * 用户登录类型静态常量<br>
 *
 * @author guqing
 * @date 2019-12-24 16:07
 */
public class LoginTypeConstant {
    /**
     * 使用邮箱登录
     */
    public static final int EMAIL = 0;
    /**
     * 使用用户名登录
     */
    public static final int USERNAME = 1;

    /**
     * 使用电话号码登录
     */
    public static final int TELEPHONE = 2;

    /**
     * 社交登录，第三方登录
     */
    public static final int OAUTH = 3;
}
