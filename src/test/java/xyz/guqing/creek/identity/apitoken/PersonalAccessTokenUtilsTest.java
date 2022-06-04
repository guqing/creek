package xyz.guqing.creek.identity.apitoken;

import static org.assertj.core.api.Assertions.assertThat;

import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;

class PersonalAccessTokenUtilsTest {

    @Test
    void generate() {
        SecretKey secretKey = PersonalAccessTokenUtils.generateSecretKey();

        for (int i = 0; i < 1000000; i++) {
            String personalAccessToken =
                PersonalAccessTokenUtils.generate(PersonalAccessTokenType.ADMIN_TOKEN, secretKey);
            boolean b = PersonalAccessTokenUtils.verifyChecksum(personalAccessToken, secretKey);
            assertThat(b).isTrue();
        }
    }
}
