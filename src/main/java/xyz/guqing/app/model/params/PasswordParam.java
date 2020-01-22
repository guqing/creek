package xyz.guqing.app.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author guqing
 * @date 2020-01-22 12:23
 */
@Data
public class PasswordParam {
    private String oldPassword = "";

    @NotBlank(message="新密码不能为空")
    @Size(min = 6, max = 16, message = "新密码长度必须在6-16字符之间")
    private String newPassword;
}
