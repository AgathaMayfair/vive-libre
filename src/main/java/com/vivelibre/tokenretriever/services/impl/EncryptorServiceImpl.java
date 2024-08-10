package com.vivelibre.tokenretriever.services.impl;

import com.vivelibre.tokenretriever.config.ApiConfig;
import com.vivelibre.tokenretriever.services.EncryptorService;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptorServiceImpl implements EncryptorService {

    private static final String ALGORITHM_KEY = "AES";

    private static final String ALGORITHM_CI = "AES/GCM/NoPadding";

    private static final int GCM_IV_LENGTH = 12;

    @Override
    public String encrypt(String rawText) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] iv = generateRandomIV();
        SecretKeySpec keySpec = new SecretKeySpec(getKey().getBytes(), ALGORITHM_KEY);
        Cipher cipher = Cipher.getInstance(ALGORITHM_CI);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new GCMParameterSpec(128, iv));
        byte[] encryptedValue = cipher.doFinal(rawText.getBytes());
        byte[] encryptedData = new byte[iv.length + encryptedValue.length];
        System.arraycopy(iv, 0, encryptedData, 0, iv.length);
        System.arraycopy(encryptedValue, 0, encryptedData, iv.length, encryptedValue.length);
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    @Override
    public String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedData = Base64.getDecoder().decode(cipherText);
        byte[] iv = new byte[GCM_IV_LENGTH];
        byte[] encryptedValue = new byte[encryptedData.length - GCM_IV_LENGTH];
        System.arraycopy(encryptedData, 0, iv, 0, GCM_IV_LENGTH);
        System.arraycopy(encryptedData, GCM_IV_LENGTH, encryptedValue, 0, encryptedValue.length);
        SecretKeySpec secretKey = new SecretKeySpec(getKey().getBytes(), ALGORITHM_KEY);
        Cipher cipher = Cipher.getInstance(ALGORITHM_CI);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));
        byte[] decryptedData = cipher.doFinal(encryptedValue);
        return new String(decryptedData);
    }

    private byte[] generateRandomIV() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return iv;
    }

    private String getKey() {
        return ApiConfig.SEED;
    }


}
