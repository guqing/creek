package xyz.guqing.creek.identity.apitoken;

/**
 * @author guqing
 * @since 2.0.0
 */
public interface PersonalAccessTokenDecoder {

    PersonalAccessToken decode(String token) throws PersonalAccessTokenException;
}
