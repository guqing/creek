package xyz.guqing.app.model.dto;

import lombok.Data;
import lombok.ToString;
import xyz.guqing.app.model.entity.Resource;
import xyz.guqing.app.model.support.OutputConverter;

/**
 * @author guqing
 * @date 2020-01-04 16:11
 */
@Data
@ToString
public class ResourceDTO implements OutputConverter<ResourceDTO, Resource> {
    private String action;
    private String description;
    private Boolean defaultCheck;
}
