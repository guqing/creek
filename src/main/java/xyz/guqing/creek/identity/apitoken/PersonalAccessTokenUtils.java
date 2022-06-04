package xyz.guqing.creek.identity.apitoken;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.CRC32;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.keygen.KeyGenerators;
import xyz.guqing.creek.infra.utils.Base62Utils;

/**
 * Tool class for generating and verifying personal access token.
 *
 * @author guqing
 * @see
 * <a href="https://github.blog/2021-04-05-behind-githubs-new-authentication-token-formats/">githubs-new-authentication-token-formats</a>
 * @since 2.0.0
 */
public class PersonalAccessTokenUtils {

    /**
     * <p>Generate personal access token through secretKey.</p>
     * <p>The format is tokenValue + 8-bit checksum.</p>
     *
     * @param secretKey The secretKey is used to generate a salt
     * @return personal access token
     */
    public static String generate(PersonalAccessTokenType tokenType, SecretKey secretKey) {
        // Generate 32-bit random API token.
        String apiToken = new String(Hex.encode(KeyGenerators.secureRandom(16).generateKey()));
        // crc32(apiToken + salt)
        String salt = convertSecretKeyToString(secretKey);
        String checksum = crc32((apiToken + salt).getBytes());
        // Encode it as base62
        String encodedValue = Base62Utils.encode(apiToken + checksum);
        return String.format("%s_%s", tokenType.value(), encodedValue);
    }

    /**
     * <p>Decoded the personalAccessToken through base62, the intercepted 8-bit checksum is
     * compared with the result generated by the checksum rule.</p>
     * <p>If it matches, it returns {@code true}, otherwise it returns {@code false}.</p>
     *
     * @param personalAccessToken personal access token to verify
     * @param secretKey The secretKey is used to generate a salt
     * @return {@code true} if the original checksum matches the generated checksum,otherwise
     * it returns {@code false}
     */
    public static boolean verifyChecksum(String personalAccessToken, SecretKey secretKey) {
        String tokenValue = PersonalTokenTypeUtils.removeTypePrefix(personalAccessToken);
        String decodedToken = Base62Utils.decodeToString(tokenValue);

        int length = decodedToken.length();
        // Gets api token and checksum from decodedToken.
        String apiToken = decodedToken.substring(0, length - 8);
        String originalChecksum = decodedToken.substring(length - 8);

        String salt = convertSecretKeyToString(secretKey);
        String checksum = crc32((apiToken + salt).getBytes());
        return StringUtils.equals(originalChecksum, checksum);
    }

    public static String convertSecretKeyToString(SecretKey secretKey) {
        byte[] rawData = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(rawData);
    }

    /**
     * <p>Generate 256 bit {@link SecretKey} through AES algorithm.</p>
     *
     * @return secret key
     */
    public static SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Convert the encoded value of 256 bit string generated by AES algorithm to
     * {@link SecretKey}.</p>
     *
     * @return secret key
     */
    public static SecretKey convertStringToSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private static String crc32(byte[] array) {
        CRC32 crc32 = new CRC32();
        crc32.update(array);
        String crcHex = Long.toHexString(crc32.getValue());
        return StringUtils.leftPad(crcHex, 8, '0');
    }
}
