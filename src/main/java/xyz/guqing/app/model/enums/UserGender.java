package xyz.guqing.app.model.enums;

import java.util.Arrays;

/**
 * @author guqing
 * @date 2019-12-24 23:28
 */
public enum  UserGender {
    /**
     * 性别为男的枚举
     */
    MALE(1, "男"),
    /**
     * 性别为女的枚举
     */
    FEMALE(0, "女"),
    /**
     * 性别未知的枚举
     */
    UNKNOWN(-1, "未知");

    private int code;
    private String desc;

    public static UserGender getRealGender(String code) {
        if (code == null) {
            return UNKNOWN;
        } else {
            String[] males = new String[]{"m", "男", "1", "male"};
            if (Arrays.asList(males).contains(code.toLowerCase())) {
                return MALE;
            } else {
                String[] females = new String[]{"f", "女", "0", "female"};
                return Arrays.asList(females).contains(code.toLowerCase()) ? FEMALE : UNKNOWN;
            }
        }
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    private UserGender(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
