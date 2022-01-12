package xyz.guqing.creek.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author guqing
 * @since 2020-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 上级菜单ID
     */
    private Long parentId;

    /**
     * 菜单的标题
     */
    private String title;

    /**
     * 组件名称
     */
    private String name;

    /**
     * 对应路由path
     */
    private String path;

    /**
     * 重定向到路径
     */
    private String redirect;

    /**
     * 对应路由组件component
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    private Integer keepAlive;

    /**
     * 控制路由和子路由是否显示在 sidebar
     */
    private Integer hidden;

    /**
     * 排序
     */
    private Long sortIndex;
}
