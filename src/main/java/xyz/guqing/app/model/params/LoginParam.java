package xyz.guqing.app.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.support.InputConverter;
import xyz.guqing.app.model.support.LoginTypeConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户登录参数<br>
 *
 * @author guqing
 * @date 2019-10-18 19:07
 */
@Data
public class LoginParam implements InputConverter<User> {
    @NotBlank(message = "用户名或邮箱不能为空")
    @Size(max = 255, message = "用户名或邮箱的字符长度不能超过 {max}")
    private String username;

    @NotBlank(message = "登陆密码不能为空")
    @ApiModelProperty(value="密码" ,required=true)
    private String password;

    private Integer loginType = LoginTypeConstant.USERNAME;
}
