package xyz.guqing.creek.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.creek.model.entity.UserGroup;
import xyz.guqing.creek.model.support.Tree;

/**
 * @author guqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupTree extends Tree<UserGroup> {
    private Integer orderIndex;
}
