package xyz.guqing.creek.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.creek.model.dto.RoleDTO;
import xyz.guqing.creek.model.entity.Role;
import xyz.guqing.creek.model.params.RoleQuery;

import java.util.List;
import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-03
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据条件查询角色信息
     * @param roleQuery 查询条件
     * @return 返回分页角色列表
     */
    Page<Role> listBy(RoleQuery roleQuery);

    /**
     * 创建或更新角色和角色关联的菜单信息
     * @param role 角色信息
     * @param authorities api scope names
     */
    void createOrUpdate(Role role, Set<String> authorities);

    /**
     * 根据角色id查询角色详情
     * @param roleId 角色id
     * @return 查询到返回角色详情否则返回{@code null}
     */
    RoleDTO getRoleById(Long roleId);

    /**
     * 删除角色
     * @param roleId 角色id
     */
    void deleteById(Long roleId);
}
