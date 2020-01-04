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
}
