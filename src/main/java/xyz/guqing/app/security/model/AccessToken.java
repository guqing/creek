package xyz.guqing.app.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import xyz.guqing.app.security.serializer.AccessTokenJacksonSerializer;

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
    private Date expiration;

    public AccessToken(String accessToken) {
        this.tokenType = "Bearer".toLowerCase();
        this.additionalInformation = Collections.emptyMap();
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return this.expiration != null ? Long.valueOf((this.expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue() : 0;
    }
}
