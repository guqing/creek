package xyz.guqing.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.guqing.app.model.entity.Role;

/**
 * @author guqing
 * @date 2019-12-21 22:11
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
