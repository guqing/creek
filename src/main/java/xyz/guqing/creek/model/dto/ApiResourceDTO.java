package xyz.guqing.creek.model.dto;

import java.util.List;
import lombok.Data;
import xyz.guqing.creek.model.entity.ApiResource;
import xyz.guqing.creek.model.support.OutputConverter;

/**
 * @author guqing
 * @since 2022-01-12
 */
@Data
public class ApiResourceDTO implements OutputConverter<ApiResourceDTO, ApiResource> {

    String name;
    String displayName;
    String description;
    List<ApiScopeDTO> scopes;
}
