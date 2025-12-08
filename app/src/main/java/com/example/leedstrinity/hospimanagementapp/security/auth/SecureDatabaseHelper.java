package com.example.leedstrinity.hospimanagementapp.security.auth;


import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecureDatabaseHelper {

    private static final String ALGORITHM = "AES";
    private static SecretKeySpec secretKey;

    // 👉 Generate a key from a passphrase
    public static void setKey(String myKey) {
        try {
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error setting key", e);
        }
    }

    // 👉 Encrypt plain text
    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)), Base64.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting", e);
        }
    }

    //  Decrypt cipher text
    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)));
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting", e);
        }
    }
}

