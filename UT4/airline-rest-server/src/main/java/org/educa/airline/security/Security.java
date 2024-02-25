package org.educa.airline.security;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@Slf4j
@Data
public class Security implements PasswordEncoder {
    private static final String CRYPT_ONE = "SHA-512";
    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return encryptOneWay(rawPassword.toString());
        } catch (Exception e) {
            log.error("Exception encode: ", e);
        }
        return null;
    }
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        boolean find = false;
        try {
            if (encode(rawPassword).equals(encodedPassword)) {
                find = true;
            }
        } catch (Exception e) {
            log.error("Exception encode: ", e);
        }
        return find;
    }
    public String encryptOneWay(String str) throws NoSuchAlgorithmException {
        MessageDigest messagedigest = MessageDigest.getInstance(CRYPT_ONE);
        messagedigest.update(str.getBytes());
        return new String(Base64.getUrlEncoder().encode(messagedigest.digest()), StandardCharsets.UTF_8);
    }
}
