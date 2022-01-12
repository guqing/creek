package xyz.guqing.creek.security.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import xyz.guqing.creek.security.AccessToken;

import java.io.IOException;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-08-20
 */
public final class AccessTokenJacksonSerializer extends JsonSerializer<AccessToken> {
    @Override
    public void serialize(AccessToken token, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("access_token", token.getAccessToken());
        jgen.writeStringField("token_type", token.getTokenType());
        String refreshToken = token.getRefreshToken();
        if (refreshToken != null) {
            jgen.writeStringField("refresh_token", refreshToken);
        }

        long expiration = token.getExpiration();
        jgen.writeNumberField("expires_in",  expiration);

        Map<String, Object> additionalInformation = token.getAdditionalInformation();
        for (String key : additionalInformation.keySet()) {
            jgen.writeObjectField(key, additionalInformation.get(key));
        }

        jgen.writeEndObject();
    }
}
