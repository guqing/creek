package xyz.guqing.creek.identity.authorization;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xyz.guqing.creek.extension.AbstractExtension;
import xyz.guqing.creek.extension.Metadata;

/**
 * @author guqing
 * @since 2.0.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractExtension {

    Metadata metadata;

    List<PolicyRule> rules;
}
