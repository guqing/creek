package xyz.guqing.app.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.app.model.entity.UserGroup;
import xyz.guqing.app.model.support.Tree;

/**
 * @author guqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupTree extends Tree<UserGroup> {
    private Integer orderIndex;
}
