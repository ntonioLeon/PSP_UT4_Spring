package org.educa.airline.security;


import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class SecurityUtil {
    private static final String CRYPT_ONE = "SHA-512";
    private static final String SEED = "CLAVE_DE_CIFRADO_DE_32_BITS_A256";
    private static final String CRYPT = "AES";

    public String crypt(String message) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec skeySpec = new SecretKeySpec(SEED.getBytes(), CRYPT);
        Cipher aesCipher = Cipher.getInstance(CRYPT);
        aesCipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] mensajeByteCifrado = aesCipher.doFinal(message.getBytes());
        byte[] mensajeByteCifradoBase64 = Base64.getEncoder().encode(mensajeByteCifrado);

        return new String(mensajeByteCifradoBase64);

    }

    public String decrypt(String mensajeCifrado) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
        SecretKeySpec skeySpec = new SecretKeySpec(SEED.getBytes(), CRYPT);
        Cipher aesCipher = Cipher.getInstance(CRYPT);
        aesCipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] mensajeByte = Base64.getDecoder().decode(mensajeCifrado.getBytes());

        return new String(aesCipher.doFinal(mensajeByte));
    }

    public String createHash(String message) throws NoSuchAlgorithmException {
        MessageDigest messagedigest;
        messagedigest = MessageDigest.getInstance(CRYPT_ONE);
        messagedigest.update(message.getBytes());

        return new String(Base64.getUrlEncoder().encode(messagedigest.digest()), StandardCharsets.UTF_8);
    }
}
