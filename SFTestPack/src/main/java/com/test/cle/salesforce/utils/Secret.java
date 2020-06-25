package com.test.cle.salesforce.utils;

import java.util.Base64;

public class Secret {

    public static String decrypt(String str) {

        System.out.println("Decrypt request for " + str);
        byte[] decodedBytes = Base64.getUrlDecoder().decode(str);

        String decryptedStr = new String(decodedBytes);

        // System.out.println("decrypted to " + decryptedStr);

        return decryptedStr;
    }
}
