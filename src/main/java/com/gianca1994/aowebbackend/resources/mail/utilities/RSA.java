package com.gianca1994.aowebbackend.resources.mail.utilities;

import com.gianca1994.aowebbackend.exception.Conflict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Author: Gianca1994
 * Explanation: This class is the service of the mail
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RSA {
    private String algorithm = "RSA";
    private String instance = "RSA/ECB/PKCS1Padding";
    private String publicKey = "";
    private String privateKey = "";

    public void setKeys(String publicKey, String privateKey) {
        /**
         * @Author: Gianca1994
         * Explanation: This method sets the keys
         * @param String publicKey
         * @param String privateKey
         * @return void
         */
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String encryptMsg(String message) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method encrypts the message
         * @param String message
         * @return String
         */
        try {
            PemObject pemObject = pemGenerator(this.publicKey);
            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance(this.algorithm).generatePublic(new X509EncodedKeySpec(pemObject.getContent()));
            Cipher cipher = Cipher.getInstance(this.instance);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new Conflict(MailConst.ERROR_ENCRYPTING);
        }
    }

    public String decryptMsg(String encryptedMessage) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method decrypts the message
         * @param String encryptedMessage
         * @return String
         */
        try {
            PemObject pemObject = pemGenerator(this.privateKey);
            RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance(this.algorithm).generatePrivate(new PKCS8EncodedKeySpec(pemObject.getContent()));
            Cipher cipher = Cipher.getInstance(this.instance);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new Conflict(MailConst.ERROR_DECRYPTING);
        }
    }

    public PemObject pemGenerator(String key) throws IOException {
        /**
         * @Author: Gianca1994
         * Explanation: This method generates the pem object
         * @param String key
         * @return PemObject
         */
        Security.addProvider(new BouncyCastleProvider());
        PemReader pemReader = new PemReader(new StringReader(key));
        return pemReader.readPemObject();
    }
}
