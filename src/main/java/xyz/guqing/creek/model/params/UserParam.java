package xyz.guqing.creek.model.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import xyz.guqing.creek.model.constant.RegexpConstant;
import xyz.guqing.creek.model.entity.User;
import xyz.guqing.creek.model.support.CreateCheck;
import xyz.guqing.creek.model.support.InputConverter;
import xyz.guqing.creek.model.support.UpdateCheck;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author guqing
 * @date 2020-06-01
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserParam implements InputConverter<User> {
    private Long id;

    @NotBlank(message = "用户名不能为空", groups = {CreateCheck.class, UpdateCheck.class})
    @Size(max = 100, message = "用户名字符长度不能超过 {max}")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "用户密码不能为空", groups = {CreateCheck.class})
    @Size(min = 3, max = 16, message = "密码长度必须在 {min}-{max} 字符之间")
    private String password;

    /**
     * 邮箱
     */
    @Pattern(regexp = RegexpConstant.EMAIL, message = "邮箱地址格式不正确")
    private String email;

    /**
     * 联系电话
     */
    @Pattern(regexp = RegexpConstant.MOBILE, message = "手机号码格式不正确")
    private String mobile;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 描述
     */
    @Size(max = 150, message = "个性签名字符长度不能大于 {max}")
    private String description;

    @NotEmpty(message = "用户角色不能为空", groups = {CreateCheck.class, UpdateCheck.class})
    private List<Long> roleIds;

    private Long groupId;

}
