package xyz.guqing.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.guqing.app.mapper.RoleMapper;
import xyz.guqing.app.model.dos.RoleDO;
import xyz.guqing.app.model.dto.RoleDTO;
import xyz.guqing.app.model.entity.Role;
import xyz.guqing.app.model.entity.UserRole;
import xyz.guqing.app.model.params.RoleQuery;
import xyz.guqing.app.model.support.QueryRequest;
import xyz.guqing.app.service.RoleMenuService;
import xyz.guqing.app.service.RoleService;
import xyz.guqing.app.service.UserRoleService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-03
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final UserRoleService userRoleService;
    private final RoleMenuService roleMenuService;

    @Override
    public void saveUserRoles(Long userId, List<Long> roleIds) {
        roleIds.forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleService.save(userRole);
        });
    }

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
        QueryRequest queryRequest = roleQuery.getQueryRequest();
        Page<Role> rolePage = new Page<>(queryRequest.getCurrent(),queryRequest.getPageSize());

        return page(rolePage, queryWrapper);
    }

    @Override
    public void createOrUpdate(Role role, Set<Long> menuIds) {
        saveOrUpdate(role);
        // 创建角色和菜单关联关系
        roleMenuService.createOrUpdate(role.getId(), menuIds);
    }

    @Override
    public RoleDTO getRoleById(Long roleId) {
        Optional<RoleDO> optionalRoleDO = this.baseMapper.findById(roleId);
        if(optionalRoleDO.isPresent()) {
            RoleDO roleDO = optionalRoleDO.get();
            return new RoleDTO().convertFrom(roleDO);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoles(List<Long> roleIds) {
        removeByIds(roleIds);

        roleMenuService.deleteByRoleIds(roleIds);
        userRoleService.deleteByRoleIds(roleIds);
    }
}
