package xyz.guqing.app.model.dto;

import lombok.Data;
import me.zhyd.oauth.model.AuthUser;

/**
 * @author guqing
 * @date 2020-05-26
 */
@Data
public class SocialLoginDTO {
    private String accessToken;
    private AuthUser authUser;
    private Boolean isBind;
}
