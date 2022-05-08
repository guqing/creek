package xyz.guqing.creek.identity.authorization;

import java.util.List;

/**
 * @author guqing
 * @since 2.0.0
 */
@FunctionalInterface
public interface RoleBindingLister {
    List<RoleBinding> listRoleBindings();
}
