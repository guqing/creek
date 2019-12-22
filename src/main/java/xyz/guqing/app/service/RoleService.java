package xyz.guqing.app.service;

import org.springframework.stereotype.Service;
import xyz.guqing.app.model.entity.Role;
import xyz.guqing.app.repository.RoleRepository;

import java.util.Optional;

/**
 * @author guqing
 * @date 2019-12-21 19:57
 */
@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findById(Integer id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        return roleOptional.orElseGet(Role::new);
    }
}
