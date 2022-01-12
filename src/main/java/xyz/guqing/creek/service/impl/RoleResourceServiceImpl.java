package xyz.guqing.creek.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.guqing.creek.exception.NotFoundException;
import xyz.guqing.creek.mapper.ApiScopeMapper;
import xyz.guqing.creek.mapper.RoleResourceMapper;
import xyz.guqing.creek.mapper.UserMapper;
import xyz.guqing.creek.model.bo.CurrentUser;
import xyz.guqing.creek.model.entity.ApiScope;
import xyz.guqing.creek.model.entity.RoleResource;
import xyz.guqing.creek.service.RoleResourceService;
import xyz.guqing.creek.utils.CreekUtils;

/**
 * @author guqing
 * @date 2020-06-09
 */
@Service
@AllArgsConstructor
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements
    RoleResourceService {
    private final ApiScopeMapper apiScopeMapper;
    private final RoleResourceMapper roleResourceMapper;
    private final UserMapper userMapper;

    @Override
    public List<RoleResource> listByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleResource> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoleResource::getRoleId, roleId);
        List<RoleResource> list = list(queryWrapper);
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list;
    }

    @Override
    public void createOrUpdate(Long roleId, Set<Long> menuIds) {
        removeByRoleId(roleId);

        RoleResource roleMenu = new RoleResource();
        roleMenu.setRoleId(roleId);
        roleMenu.setMenus(StringUtils.join(menuIds, ","));
        save(roleMenu);
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<RoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RoleResource::getRoleId, roleIds);
        remove(queryWrapper);
    }

    @Override
    public List<ApiScope> listScopesByIds(List<Long> roleIds) {
        Set<Long> scopeIds = listByIds(roleIds).stream()
            .map(RoleResource::getScopes)
            .map(CreekUtils::splitToLong)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        return apiScopeMapper.selectBatchIds(scopeIds);
    }

    @Override
    public Set<Long> listMenuIdsByUsername(String username) {
        CurrentUser user = userMapper.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("访问资源不存在"));
        List<Long> roleIds = CreekUtils.splitToLong(user.getRoleIds());
        return roleResourceMapper.selectList(Wrappers.lambdaQuery(RoleResource.class)
                .in(RoleResource::getRoleId, roleIds))
            .stream()
            .map(RoleResource::getMenus)
            .map(CreekUtils::splitToLong)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    private void removeByRoleId(Long roleId) {
        // 根据角色id删除
        LambdaQueryWrapper<RoleResource> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoleResource::getRoleId, roleId);
        remove(queryWrapper);
    }
}
