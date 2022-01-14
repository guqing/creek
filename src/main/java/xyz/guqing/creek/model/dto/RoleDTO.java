package xyz.guqing.creek.model.dto;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import xyz.guqing.creek.model.entity.Role;
import xyz.guqing.creek.model.support.OutputConverter;

/**
 * @author guqing
 * @date 2020-06-04
 */
@Data
public class RoleDTO implements OutputConverter<RoleDTO, Role> {

    private Long id;
    private String roleName;
    private String remark;
    private Boolean isInternal;
    private Set<String> authorities;
    private LocalDateTime createTime;
}
