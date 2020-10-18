package xyz.guqing.creek.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author guqing
 * @date 2020-10-14
 */
@Data
public class ChangePasswordParam {
    @NotBlank(message = "原始密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    @Size(max = 16, message = "密码字符长度不能超过 {max}")
    private String newPassword;
}
