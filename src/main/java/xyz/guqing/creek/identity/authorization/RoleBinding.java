package xyz.guqing.creek.identity.authorization;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xyz.guqing.creek.extension.AbstractExtension;

/**
 * RoleBinding references a role, but does not contain it.  It can reference a Role in the
 * same namespace or a ClusterRole in the global namespace.
 * It adds who information via Subjects and namespace information by which namespace it exists
 * in.
 *
 * @author guqing
 * @since 2.0.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RoleBinding extends AbstractExtension {
    /**
     * Subjects holds references to the objects the role applies to.
     */
    List<Subject> subjects;

    /**
     * RoleRef can reference a Role in the current namespace or a ClusterRole in the global
     * namespace.
     * If the RoleRef cannot be resolved, the Authorizer must return an error.
     */
    RoleRef roleRef;
}
