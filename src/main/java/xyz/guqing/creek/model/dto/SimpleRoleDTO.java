package xyz.guqing.creek.model.dto;

import lombok.Data;
import xyz.guqing.creek.model.entity.Role;
import xyz.guqing.creek.model.support.OutputConverter;

/**
 * @author guqing
 * @since 2022-01-14
 */
@Data
public class SimpleRoleDTO implements OutputConverter<SimpleRoleDTO, Role> {

    private Long id;
    private String roleName;
}
