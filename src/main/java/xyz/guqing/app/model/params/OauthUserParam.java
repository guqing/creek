package xyz.guqing.app.model.params;

import lombok.Data;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.support.InputConverter;
import xyz.guqing.app.model.support.UserStatusConstant;

import java.util.Date;

/**
 * @author guqing
 * @date 2019-12-24 23:13
 */
@Data
public class OauthUserParam implements InputConverter<User> {
    private String username;
    private String nickname;
    private Integer gender;
    private String location;
    private String blog;
    private String remark;
    private String avatar;
    private String source;
    private String uuid;
    private String email;

    @Override
    public User convertTo() {
        User user = InputConverter.super.convertTo();
        user.setDeleted(UserStatusConstant.NORMAL);
        user.setStatus(UserStatusConstant.NORMAL);
        user.setDescription(remark);
        return user;
    }
}
