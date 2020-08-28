package xyz.guqing.app.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色菜单关联表
 * </p>
 *
 * @author guqing
 * @since 2020-08-19
 */
@Data
@Accessors(chain = true)
@TableName("role_menu")
public class RoleMenu {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long menuId;
}
