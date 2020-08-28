package xyz.guqing.app.model.enums;

/**
 * @author guqing
 * @date 2020-06-04
 */
public enum MenuType {
    /**
     * 菜单类型，0菜单，1按钮
     */
    MENU("0"),
    BUTTON("1");
    private String value;

    MenuType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String valueFrom(String type) {
        for(MenuType menuType : values()) {
            if(menuType.getValue().equals(type)) {
                return type;
            }
        }
        return MENU.getValue();
    }
}
