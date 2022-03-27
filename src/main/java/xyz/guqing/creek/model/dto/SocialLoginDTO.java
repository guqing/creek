package xyz.guqing.creek.model.dto;

import lombok.Data;
import me.zhyd.oauth.model.AuthUser;
import xyz.guqing.creek.model.entity.OauthAccessToken;

/**
 * @author guqing
 * @date 2020-05-26
 */
@Data
public class SocialLoginDTO {

    private OauthAccessToken accessToken;
    private AuthUser authUser;
    private Boolean isBind;
}
