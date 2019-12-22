package xyz.guqing.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.guqing.app.model.entity.Permission;
import xyz.guqing.app.model.entity.Role;
import xyz.guqing.app.repository.PermissionRepository;

import java.util.List;
import java.util.Set;

/**
 * @author guqing
 * @date 2019-12-21 19:57
 */
@Service
public class PermissionService {
    private PermissionRepository permissionRepository;
    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Set<Permission> findByRoleId(Integer roleId) {
        return permissionRepository.findByRoleId(roleId);
    }
}
