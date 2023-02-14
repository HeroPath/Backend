package com.gianca1994.aowebbackend.resources.mail.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RSA {
    private String publicKey;
    private String privateKey;

   private String encrypt(String message) {
        return null;
    }

    private String decrypt(String message) {
        return null;
    }
}
