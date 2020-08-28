package xyz.guqing.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.app.model.entity.RoleMenu;

import java.util.List;
import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-09
 */
public interface RoleMenuService extends IService<RoleMenu> {
    /**
     * 根据角色id查询角色和菜单的关联关系
     * @param roleId 角色id
     * @return 查询到返回集合信息否则返回空集合
     */
    List<RoleMenu> listByRoleId(Long roleId);

    /**
     * 创建或跟新角色和菜单关联关系
     * @param roleId 角色id
     * @param menuIds 角色对应的菜单id集合
     */
    void createOrUpdate(Long roleId, Set<Long> menuIds);

    /**
     * 根据角色id集合批量删除关联关系
     * @param roleIds 菜单id集合
     */
    void deleteByRoleIds(List<Long> roleIds);
}
