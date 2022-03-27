package xyz.guqing.creek.model.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import xyz.guqing.creek.model.entity.Role;
import xyz.guqing.creek.model.support.InputConverter;
import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-09
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleParam implements InputConverter<Role> {
    private Long id;

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 140, message = "角色名称字符长度不能超过 {max}")
    private String roleName;

    private String remark;

    @NotNull
    private Set<String> authorities;
}
