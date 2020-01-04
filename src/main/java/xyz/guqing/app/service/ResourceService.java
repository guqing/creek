package xyz.guqing.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.guqing.app.model.entity.Resource;
import xyz.guqing.app.repository.ResourceRepository;

import java.util.List;

/**
 * @author guqing
 * @date 2020-01-04 15:22
 */
@Service
public class ResourceService {
    private ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<Resource> findByRoleId(Integer roleId) {
        return resourceRepository.findByRoleId(roleId);
    }
}