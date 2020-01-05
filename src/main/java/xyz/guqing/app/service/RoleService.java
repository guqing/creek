package xyz.guqing.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import xyz.guqing.app.model.dto.RoleDTO;
import xyz.guqing.app.model.entity.Role;
import xyz.guqing.app.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
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

    public Page<Role> findAllByPage(Integer current, Integer pageSize) {
        return roleRepository.findAll(PageRequest.of(current - 1, pageSize));
    }

    public Long count() {
        return roleRepository.count();
    }
}
