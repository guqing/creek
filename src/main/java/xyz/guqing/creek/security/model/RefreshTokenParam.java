package xyz.guqing.creek.security.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author guqing
 * @date 2020-11-11
 */
@Data
public class RefreshTokenParam {
    @NotBlank(message = "refresh token不能为空")
    private String token;
}
