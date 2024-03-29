package com.gianca1994.heropathbackend.resources.mail.encryption;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author: Gianca1994
 * @Explanation: This class is the service of the mail
 */

@Getter
@Setter
@NoArgsConstructor
public class AES {
    private String key = "B043CF915C58E8F356E8A1EE63FF8626";
    private String instance = "AES/ECB/PKCS5Padding";
    private String algorithm = "AES";

    public String encryptMsg(String message) throws Exception {
        /**
         * @Author: Gianca1994
         * @Explanation: This method encrypts the message
         * @param String message
         * @return String
         */
        Cipher cipher = Cipher.getInstance(instance);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptMsg(String encryptedMessage) throws Exception {
        /**
         * @Author: Gianca1994
         * @Explanation: This method decrypts the message
         * @param String encryptedMessage
         * @return String
         */
        Cipher cipher = Cipher.getInstance(instance);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
