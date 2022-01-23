package xyz.guqing.creek.security.store;

import lombok.Data;

/**
 * @author guqing
 * @since 2022-01-23
 */
@Data
public class OauthRefreshToken {
    private String tokenId;
    private String token;
}
