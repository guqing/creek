package xyz.guqing.app.model.support;

import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.BeanUtils;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.entity.UserConnect;
import xyz.guqing.app.model.params.OauthUserParam;

import java.util.Date;

/**
 * 将Just Auth 的AuthUser转换为UserConnect对象
 * @author guqing
 * @date 2019-12-25 10:52
 */
public class UserConnectConverter {
    private UserConnectConverter(){}
    public static UserConnect convertTo(AuthUser authUser, String ip) {
        // 根据AuthUser创建UserConnect
        UserConnect userConnect = new UserConnect();
        userConnect.setUuid(authUser.getUuid());
        userConnect.setProviderId(authUser.getSource());
        AuthToken authToken = authUser.getToken();
        userConnect.setOpenId(authToken.getOpenId());
        userConnect.setAccessToken(authToken.getAccessToken());
        userConnect.setTokenType(authToken.getTokenType());
        userConnect.setExpireIn(authToken.getExpireIn());

        // 拷贝User属性
        OauthUserParam oauthUserParam = new OauthUserParam();
        // source->target
        BeanUtils.copyProperties(authUser, oauthUserParam);
        AuthUserGender gender = authUser.getGender();
        oauthUserParam.setGender(gender.getCode());
        User user = oauthUserParam.convertTo();
        userConnect.setUser(user);
        user.setLastLoginIp(ip);
        user.setLastLoginTime(new Date());
        user.setBirthday(new Date());
        return userConnect;
    }
}
