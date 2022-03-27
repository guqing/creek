package xyz.guqing.creek.model.params;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import xyz.guqing.creek.model.constant.RegexpConstant;


/**
 * @author guqing
 * @date 2020-07-15
 */
@Data
public class BindUserParam {
    @NotBlank(message = "邮箱地址不能为空")
    @Pattern(regexp = RegexpConstant.EMAIL, message = "邮箱地址格式不正确")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Size(min = 3, max = 16, message = "密码字符长度必须在 {min}-{max} 之间")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @NotNull(message = "第三方认证用户信息不能为空")
    private SocialUserParam socialUserParam;
}
