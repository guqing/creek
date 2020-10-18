package xyz.guqing.creek.model.dto;

import lombok.Data;
import xyz.guqing.creek.model.entity.Menu;
import xyz.guqing.creek.model.support.OutputConverter;

/**
 * @author guqing
 * @date 2020-07-18
 */
@Data
public class MenuDTO implements OutputConverter<MenuDTO, Menu> {
    /**
     * 没有id玩不转的
     */
    private Long id;

    /**
     * 上级菜单ID
     */
    private Long parentId;

    /**
     * 菜单路由名称，英文
     */
    private String name;

    /**
     * 菜单/按钮的标题
     */
    private String title;

    /**
     * 是否显示sidebar
     */
    private Boolean hidden;

    private Boolean keepAlive;

    /**
     * 对应路由path
     */
    private String path;

    /**
     * 菜单的重定向路径
     */
    private String redirect;

    /**
     * 对应路由组件component
     */
    private String component;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    private String type;

    /**
     * 排序
     */
    private Long sortIndex;
}
