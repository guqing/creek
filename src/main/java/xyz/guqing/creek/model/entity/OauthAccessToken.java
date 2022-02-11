package xyz.guqing.creek.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("oauth_access_token")
public class OauthAccessToken {
    @TableId(type = IdType.INPUT)
    private String tokenId;

    private String token;

    private String authenticationId;

    private String authentication;

    private String refreshToken;

    private String userName;
}
