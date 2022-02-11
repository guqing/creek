package xyz.guqing.creek.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author guqing
 * @since 2022-01-23
 */
@Data
@TableName("oauth_refresh_token")
public class OauthRefreshToken {
    private String tokenId;
    private String token;
}
