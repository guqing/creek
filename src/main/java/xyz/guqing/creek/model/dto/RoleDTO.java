package xyz.guqing.creek.model.dto;

import lombok.Data;
import xyz.guqing.creek.model.entity.Role;
import xyz.guqing.creek.model.support.OutputConverter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-04
 */
@Data
public class RoleDTO implements OutputConverter<RoleDTO, Role> {
    private Long id;
    private String roleName;
    private String remark;
    private LocalDateTime createTime;
    private Set<Long> menuIds;
}
