package uz.ciasev.ubdd_service.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.exception.EncryptionError;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public final class EncryptUtils {

    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private final SecretKeySpec secretKey;


    public static String encodePassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] stringBytes = plainText.getBytes(StandardCharsets.UTF_8);
            return encoder.encodeToString(cipher.doFinal(stringBytes));
        } catch (Exception e) {
            throw new EncryptionError(e.getMessage());
        }
    }

    public String decrypt(String encryptedString) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            Base64.Decoder decoder = Base64.getDecoder();
            return new String(cipher.doFinal(decoder.decode(encryptedString)));
        } catch (Exception e) {
            throw new EncryptionError(e.getMessage());
        }
    }
}