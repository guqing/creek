package xyz.guqing.creek.identity.authorization;

import java.util.List;
import lombok.Data;
import xyz.guqing.creek.infra.types.ObjectMeta;
import xyz.guqing.creek.infra.types.TypeMeta;

/**
 * @author guqing
 * @since 2.0.0
 */
@Data
public class Role {

    TypeMeta typeMeta;

    ObjectMeta objectMeta;

    List<PolicyRule> rules;
}
