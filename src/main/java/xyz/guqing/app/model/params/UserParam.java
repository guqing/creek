package xyz.guqing.app.model.params;

import lombok.Data;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.support.InputConverter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 用户信息参数类<br>
 *
 * @author guqing
 * @date 2019-12-23 22:41
 */
@Data
public class UserParam implements InputConverter<User> {
    private Integer id;

    @NotBlank(message="昵称不能为空")
    private String name;

    private String username;

    @Max(value=144, message="长度必须小于144个字符")
    private String description;

    @Pattern(regexp="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message="手机号格式不正确")
    private String telephone;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\\\.[a-zA-Z0-9_-]+)+$", message = "邮箱地址格式不正确")
    private String email;

    private String avatar;

    private Integer gender;

    private String lastLoginIp;

    private Date lastLoginTime;
}
