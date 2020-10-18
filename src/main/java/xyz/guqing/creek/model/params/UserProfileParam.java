package xyz.guqing.creek.model.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import xyz.guqing.creek.model.constant.RegexpConstant;
import xyz.guqing.creek.model.entity.User;
import xyz.guqing.creek.model.support.InputConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用户个人主页参数
 * @author guqing
 * @date 2020-10-12
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileParam implements InputConverter<User> {
    @NotBlank(message = "昵称不能为空")
    @Size(max = 30, message = "昵称字符长度不能超过 {max}")
    private String nickname;
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
     * 描述
     */
    @Size(max = 150, message = "个性签名字符长度不能大于 {max}")
    private String description;
}
