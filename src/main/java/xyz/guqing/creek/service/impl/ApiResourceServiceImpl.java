package xyz.guqing.creek.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.guqing.creek.mapper.ApiResourceMapper;
import xyz.guqing.creek.mapper.ApiScopeMapper;
import xyz.guqing.creek.model.dto.ApiResourceDTO;
import xyz.guqing.creek.model.dto.ApiScopeDTO;
import xyz.guqing.creek.model.entity.ApiResource;
import xyz.guqing.creek.model.entity.ApiScope;
import xyz.guqing.creek.service.ApiResourceService;

/**
 * @author guqing
 * @since 2022-01-12
 */
@Service
@AllArgsConstructor
public class ApiResourceServiceImpl extends ServiceImpl<ApiResourceMapper, ApiResource>
    implements ApiResourceService {

    private final ApiScopeMapper apiScopeMapper;

    @Override
    public List<ApiResourceDTO> listWithScopes() {
        return list().stream().map(apiResource -> {
                ApiResourceDTO resourceDTO = new ApiResourceDTO().convertFrom(apiResource);
                List<ApiScopeDTO> scopes = listScopesByResourceId(apiResource.getId());
                resourceDTO.setScopes(scopes);
                return resourceDTO;
            })
            .collect(Collectors.toList());
    }

    private List<ApiScopeDTO> listScopesByResourceId(Long resourceId) {
        return apiScopeMapper.selectList(Wrappers.lambdaQuery(ApiScope.class)
                .eq(ApiScope::getResourceId, resourceId)).stream()
            .map(scope -> (ApiScopeDTO) new ApiScopeDTO().convertFrom(scope))
            .collect(Collectors.toList());
    }
}
