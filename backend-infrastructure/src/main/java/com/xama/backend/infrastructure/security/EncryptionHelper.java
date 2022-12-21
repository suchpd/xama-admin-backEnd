package com.xama.backend.infrastructure.security;

import com.xama.backend.infrastructure.utils.TypeHelper;
import lombok.SneakyThrows;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class EncryptionHelper {
    private static final String characterEncoding = "UTF-8";
    private static final String cipherTransformation = "AES/CBC/PKCS5Padding";
    private static final String aesEncryptionAlgorithm = "AES";

    public EncryptionHelper() {
    }

    public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpecy = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(2, secretKeySpecy, ivParameterSpec);
        cipherText = cipher.doFinal(cipherText);
        return cipherText;
    }

    public static byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(1, secretKeySpec, ivParameterSpec);
        plainText = cipher.doFinal(plainText);
        return plainText;
    }

    private static byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
        byte[] keyBytes = new byte[16];
        byte[] parameterKeyBytes = key.getBytes("UTF-8");
        System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
        return keyBytes;
    }

//    public static String encrypt(String plainText) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//        byte[] plainTextbytes = plainText.getBytes("UTF-8");
//        String key = TypeHelper.uuidToUUIDHex(UUID.randomUUID());
//        byte[] keyBytes = getKeyBytes(key);
//        return TypeHelper.bytesToHex(encrypt(plainTextbytes, keyBytes, keyBytes)) + key;
//    }
//
//    public static String decrypt(String encryptedText) throws GeneralSecurityException, IOException {
//        String key = encryptedText.substring(encryptedText.length() - 32);
//        String encrypted = encryptedText.substring(0, encryptedText.length() - 32);
//        byte[] cipheredBytes = TypeHelper.getBytesFromHexString(encrypted);
//        byte[] keyBytes = getKeyBytes(key);
//        return new String(decrypt(cipheredBytes, keyBytes, keyBytes), "UTF-8");
//    }

    @SneakyThrows
    public static String encrypt(String plainText){
        byte[] plainTextbytes = plainText.getBytes("UTF-8");
        String key = TypeHelper.uuidToUUIDHex(UUID.randomUUID());
        byte[] keyBytes = getKeyBytes(key);
        return TypeHelper.bytesToHex(encrypt(plainTextbytes, keyBytes, keyBytes)) + key;
    }

    @SneakyThrows
    public static String decrypt(String encryptedText) {
        String key = encryptedText.substring(encryptedText.length() - 32);
        String encrypted = encryptedText.substring(0, encryptedText.length() - 32);
        byte[] cipheredBytes = TypeHelper.getBytesFromHexString(encrypted);
        byte[] keyBytes = getKeyBytes(key);
        return new String(decrypt(cipheredBytes, keyBytes, keyBytes), "UTF-8");
    }
}
