package xyz.guqing.creek.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.jsonwebtoken.lang.Assert;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.guqing.creek.exception.NotFoundException;
import xyz.guqing.creek.mapper.ApiScopeMapper;
import xyz.guqing.creek.mapper.RoleResourceMapper;
import xyz.guqing.creek.mapper.UserMapper;
import xyz.guqing.creek.model.bo.CurrentUser;
import xyz.guqing.creek.model.dto.ApiResourceDTO;
import xyz.guqing.creek.model.dto.ApiScopeDTO;
import xyz.guqing.creek.model.entity.ApiScope;
import xyz.guqing.creek.model.entity.RoleResource;
import xyz.guqing.creek.service.ApiResourceService;
import xyz.guqing.creek.service.RoleResourceService;
import xyz.guqing.creek.utils.CreekUtils;
import xyz.guqing.creek.utils.ServiceUtils;

/**
 * @author guqing
 * @date 2020-06-09
 */
@Service
@AllArgsConstructor
public class RoleResourceServiceImpl implements RoleResourceService {
    private final ApiScopeMapper apiScopeMapper;
    private final RoleResourceMapper roleResourceMapper;
    private final UserMapper userMapper;
    private final ApiResourceService apiResourceService;

    @Override
    public List<RoleResource> listByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleResource> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoleResource::getRoleId, roleId);
        return roleResourceMapper.selectList(queryWrapper);
    }

    @Override
    public List<ApiResourceDTO> getResourceByRoleId(Long roleId) {
        Assert.notNull(roleId, "The roleId must not be null.");
        List<ApiScope> apiScopes = listScopesByRoleIds(List.of(roleId));
        Set<Long> resourceIds = ServiceUtils.fetchProperty(apiScopes, ApiScope::getResourceId);
        Map<Long, List<ApiScope>> resourceIdScopesMap =
            ServiceUtils.convertToListMap(resourceIds, apiScopes, ApiScope::getResourceId);
        return apiResourceService.listByIds(resourceIds)
            .stream()
            .map(resource -> {
                ApiResourceDTO resourceDTO = new ApiResourceDTO().convertFrom(resource);

                List<ApiScope> scopes = resourceIdScopesMap.get(resource.getId());
                if (scopes != null) {
                    List<ApiScopeDTO> scopeDtoList = scopes.stream()
                        .map(scope -> (ApiScopeDTO) new ApiScopeDTO().convertFrom(scope))
                        .collect(Collectors.toList());
                    resourceDTO.setScopes(scopeDtoList);
                }
                return resourceDTO;
            })
            .collect(Collectors.toList());
    }


    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }
        LambdaQueryWrapper<RoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RoleResource::getRoleId, roleIds);
        roleResourceMapper.delete(queryWrapper);
    }

    @Override
    public Set<String> listScopeNameByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptySet();
        }
        return roleResourceMapper.selectList(
                Wrappers.lambdaQuery(RoleResource.class).in(RoleResource::getRoleId, roleIds))
            .stream()
            .map(RoleResource::getScope)
            .collect(Collectors.toSet());
    }

    @Override
    public void createOrUpdate(Long roleId, Set<String> authorities) {
        Assert.notNull(roleId, "The roleId must not be null.");
        roleResourceMapper.delete(Wrappers.lambdaQuery(RoleResource.class)
            .eq(RoleResource::getRoleId, roleId));
        for (String authority : authorities) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setScope(authority);
            roleResourceMapper.insert(roleResource);
        }
    }

    @Override
    public List<ApiScope> listScopesByRoleIds(List<Long> roleIds) {
        Set<String> scopeNames = listScopeNameByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(scopeNames)) {
            return Collections.emptyList();
        }
        return apiScopeMapper.selectList(Wrappers.lambdaQuery(ApiScope.class)
            .in(ApiScope::getName, scopeNames));
    }

    private List<Long> listRoleIdsByUsername(String username) {
        CurrentUser user = userMapper.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("访问资源不存在"));
        return CreekUtils.splitToLong(user.getRoleIds());
    }

    @Override
    public List<ApiScope> listScopesByUsername(String username) {
        List<Long> roleIds = listRoleIdsByUsername(username);
        return listScopesByRoleIds(roleIds);
    }
}
