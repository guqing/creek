package xyz.guqing.creek.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.guqing.creek.mapper.RoleMapper;
import xyz.guqing.creek.model.dto.RoleDTO;
import xyz.guqing.creek.model.entity.Role;
import xyz.guqing.creek.model.params.RoleQuery;
import xyz.guqing.creek.model.support.PageQuery;
import xyz.guqing.creek.service.RoleResourceService;
import xyz.guqing.creek.service.RoleService;

/**
 * @author guqing
 * @date 2020-06-03
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final RoleResourceService roleResourceService;

    @Override
    public Page<Role> listBy(RoleQuery roleQuery) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery();
        if(roleQuery.getId() != null) {
            queryWrapper.like(Role::getId, roleQuery.getId());
        }

        if(roleQuery.getRoleName() != null) {
            queryWrapper.like(Role::getRoleName, roleQuery.getRoleName());
        }

        if(roleQuery.getRemark() != null) {
            queryWrapper.like(Role::getRemark, roleQuery.getRemark());
        }
        queryWrapper.orderByAsc(Role::getCreateTime);
        PageQuery pageQuery = roleQuery.getPageQuery();
        Page<Role> rolePage = new Page<>(pageQuery.getCurrent(), pageQuery.getPageSize());

        return page(rolePage, queryWrapper);
    }

    @Override
    public void createOrUpdate(Role role, Set<String> authorities) {
        saveOrUpdate(role);
        // 创建角色和权限关联关系
        roleResourceService.createOrUpdate(role.getId(), authorities);
    }

    @Override
    public RoleDTO getRoleById(Long roleId) {
        Assert.notNull(roleId, "The roleId must not be null.");
        Role role = getById(roleId);
        if (role == null) {
            return null;
        }
        RoleDTO roleDTO = new RoleDTO().convertFrom(role);
        Set<String> authorities = roleResourceService.listScopeNameByRoleIds(List.of(roleId));
        roleDTO.setAuthorities(authorities);
        return roleDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        roleResourceService.deleteByRoleId(id);
        super.removeById(id);
    }
}
