package xyz.guqing.creek.model.params;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import xyz.guqing.creek.model.entity.UserGroup;
import xyz.guqing.creek.model.support.InputConverter;


/**
 * @author guqing
 * @date 2020-06-06
 */
@Data
public class UserGroupParam implements InputConverter<UserGroup> {
    private Long id;

    private Long parentId;

    @NotBlank(message = "用户组名称不能为空")
    @Size(max = 150, message = "用户组名称字符长度不能超过 {max}")
    private String groupName;

    private Long sortIndex;
}
