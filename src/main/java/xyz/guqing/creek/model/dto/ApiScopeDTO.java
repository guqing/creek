package xyz.guqing.creek.model.dto;

import lombok.Data;
import xyz.guqing.creek.model.entity.ApiScope;
import xyz.guqing.creek.model.support.OutputConverter;

/**
 * @author guqing
 * @since 2022-01-12
 */
@Data
public class ApiScopeDTO implements OutputConverter<ApiScopeDTO, ApiScope> {

    private String name;
    private String displayName;
    private String description;
}
