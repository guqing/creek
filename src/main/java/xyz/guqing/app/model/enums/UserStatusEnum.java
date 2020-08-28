package xyz.guqing.app.model.enums;

/**
 * @author guqing
 * @date 2020-06-03
 */
public enum UserStatusEnum {
    /**
     * 用户状态
     */
    NORMAL(0),
    DISABLE(1);

    private Integer value;

    UserStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
