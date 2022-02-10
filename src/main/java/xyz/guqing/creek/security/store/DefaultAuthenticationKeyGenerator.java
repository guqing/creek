package xyz.guqing.creek.security.store;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author guqing
 * @date 2022-02-10
 */
public class DefaultAuthenticationKeyGenerator implements AuthenticationKeyGenerator {

    private static final String SCOPE = "scope";

    private static final String USERNAME = "username";

    public String extractKey(OAuth2Authentication authentication) {
        Map<String, String> values = new LinkedHashMap<String, String>();
        OAuth2Request authorizationRequest = authentication.getOAuth2Request();
        if (!authentication.isClientOnly()) {
            values.put(USERNAME, authentication.getName());
        }
        if (authorizationRequest.getScope() != null) {
            values.put(SCOPE, formatParameterList(new TreeSet<String>(authorizationRequest.getScope())));
        }
        return generateKey(values);
    }

    /**
     * Formats a set of string values into a format appropriate for sending as a single-valued form value.
     *
     * @param value The value of the parameter.
     * @return The value formatted for form submission etc, or null if the input is empty
     */
    public static String formatParameterList(Collection<String> value) {
        return value == null ? null : StringUtils.collectionToDelimitedString(value, " ");
    }

    protected String generateKey(Map<String, String> values) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (NoSuchAlgorithmException nsae) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", nsae);
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", uee);
        }
    }
}
