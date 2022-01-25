package xyz.guqing.creek.security.store;

import java.util.Date;
import java.util.Set;
import lombok.Data;

/**
 * @author guqing
 * @since 2022-01-23
 */
@Data
public class OAuthAccessToken {
    private String tokenId;
    private String token;
    private String value;
    private Date expiration;
    private Set<String> scope;
    private String username;
}
