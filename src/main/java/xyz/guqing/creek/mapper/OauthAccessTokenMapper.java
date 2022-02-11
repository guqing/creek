package xyz.guqing.creek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Select;
import xyz.guqing.creek.model.entity.OauthAccessToken;

public interface OauthAccessTokenMapper extends BaseMapper<OauthAccessToken> {

    @Select("select token_id, token from oauth_access_token where authentication_id = #{authenticationId}")
    OauthAccessToken selectFromAuthentication(String authenticationId);

    @Select("select token_id, token from oauth_access_token where user_name = #{username}")
    List<OauthAccessToken> selectByUsername(String username);
}
