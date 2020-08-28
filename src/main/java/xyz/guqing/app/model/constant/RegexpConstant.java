package xyz.guqing.app.model.constant;

/**
 * 正则常量
 *
 * @author guqing
 */
public interface RegexpConstant {

    /**
     * 简单手机号正则（这里只是简单校验是否为 11位，实际规则更复杂）
     */
    String MOBILE = "^(?:(?:\\+|00)86)?1[3-9]\\d{9}$";
    String EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
}
