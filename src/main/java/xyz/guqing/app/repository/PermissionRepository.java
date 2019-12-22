package xyz.guqing.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import xyz.guqing.app.model.entity.Permission;

import java.util.Set;

/**
 * @author guqing
 * @date 2019-12-22 13:01
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    /**
     * 根据角色id查询权限
     * @param roleId 角色id
     * @return 返回资源列表
     */
    @NonNull
    Set<Permission> findByRoleId(@NonNull Integer roleId);
}
