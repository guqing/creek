package xyz.guqing.creek.model.dos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.creek.model.entity.Role;

import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDO extends Role {
    private Set<Long> menuIds;
}
