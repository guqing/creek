package xyz.guqing.creek.model.params;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import xyz.guqing.creek.model.entity.Credentials;
import xyz.guqing.creek.model.support.InputConverter;

/**
 * @author guqing
 * @since 2022-01-17
 */
@Data
public class CredentialsParam implements InputConverter<Credentials> {

    @NotBlank(message = "备注不能为空")
    private String remark;

    @NotNull(message = "权限域不能为空")
    private Set<String> scopes;

    @NotNull(message = "过期时间不能为空")
    private LocalDateTime expiredAt;
}
