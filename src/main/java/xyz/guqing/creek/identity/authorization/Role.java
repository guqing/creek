package xyz.guqing.creek.identity.authorization;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xyz.guqing.creek.extension.AbstractExtension;
import xyz.guqing.creek.extension.GVK;

/**
 * @author guqing
 * @since 2.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@GVK(group = "", version = "v1alpha1", kind = "Role", plural = "roles", singular = "role")
public class Role extends AbstractExtension {

    @Schema(minLength = 1)
    List<PolicyRule> rules;
}
