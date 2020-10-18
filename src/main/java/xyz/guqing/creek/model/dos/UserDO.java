package xyz.guqing.creek.model.dos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.creek.model.entity.User;

/**
 * user 数据实体
 * @author guqing
 * @date 2020-05-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDO extends User {
    private String groupName;
    private String roleId;
    private String roleName;
}
