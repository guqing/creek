package xyz.guqing.creek.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Collection;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色资源表，存储角色关联的菜单和api资源
 * </p>
 *
 * @author guqing
 * @since 2020-08-19
 */
@Data
@Accessors(chain = true)
@TableName("role_resource")
public class RoleResource {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Long roleId;

    private String scope;
}
