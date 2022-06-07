package com.example.chatapp.tools;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

    private Cipher cipher, decipher;
    private KeySpec spec;
    private SecretKey pbeSecretKey;
    private SecretKey aesSecret;
    private SecretKeyFactory secKeyFactory;
    private String password = "isTrue";

    public Crypto() {
        try {
            secKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            cipher = Cipher.getInstance("AES/GCM/NoPadding");;
            decipher = Cipher.getInstance("AES/GCM/NoPadding");;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }

    public String ASEEncryption(String string, Long salt) {
        spec = new PBEKeySpec(password.toCharArray(), Long.toString(salt).getBytes(),
                65556, 128);
        try {
            pbeSecretKey = secKeyFactory.generateSecret(spec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        aesSecret = new SecretKeySpec(pbeSecretKey.getEncoded(), "AES");

        byte[] stringByte = string.getBytes();
        byte[] encryptedByte = new byte[stringByte.length];

        try {
            cipher.init(Cipher.ENCRYPT_MODE, aesSecret, new GCMParameterSpec(128,
                    Long.toString(salt).getBytes()));
            encryptedByte = cipher.doFinal(stringByte);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        String returnString = null;

        returnString = new String(encryptedByte, StandardCharsets.ISO_8859_1);

        return  returnString;
    }

    public String AESDecryption(String string, Long salt) throws UnsupportedEncodingException {
        spec = new PBEKeySpec(password.toCharArray(), Long.toString(salt).getBytes(),
                65556, 128);
        try {
            pbeSecretKey = secKeyFactory.generateSecret(spec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        aesSecret = new SecretKeySpec(pbeSecretKey.getEncoded(), "AES");

        byte[] encryptedByte = string.getBytes(StandardCharsets.ISO_8859_1);
        String decryptedString = string;

        byte[] decryption;

        try {
            decipher.init(cipher.DECRYPT_MODE, aesSecret, new GCMParameterSpec(128,
                    Long.toString(salt).getBytes()));
            decryption = decipher.doFinal(encryptedByte);
            decryptedString = new String(decryption);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return decryptedString;
    }
}
