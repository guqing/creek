package xyz.guqing.app.model.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.model.AuthUser;
import xyz.guqing.app.model.support.InputConverter;

/**
 * @author guqing
 * @date 2020-07-28
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialUserParam implements InputConverter<AuthUser> {
    private String uuid;
    private String username;
    private String nickname;
    private String avatar;
    private String blog;
    private String company;
    private String location;
    private String email;
    private String remark;
    private AuthUserGender gender;
    private AuthSource source;
}
