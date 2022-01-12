package xyz.guqing.creek.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import xyz.guqing.creek.security.serializer.AccessTokenJacksonSerializer;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-08-20
 */
@Data
@JsonSerialize(
        using = AccessTokenJacksonSerializer.class
)
public class AccessToken implements Serializable {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private Map<String, Object> additionalInformation;

    @JsonIgnore
    private long expiration;

    public AccessToken(String accessToken) {
        this.tokenType = "Bearer".toLowerCase();
        this.additionalInformation = Collections.emptyMap();
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return this.expiration;
    }
}
