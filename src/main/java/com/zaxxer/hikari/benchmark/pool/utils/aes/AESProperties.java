package com.zaxxer.hikari.benchmark.pool.utils.aes;

import com.fasterxml.jackson.databind.JsonNode;
import com.zaxxer.hikari.benchmark.pool.utils.Utils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;

public enum AESProperties {
    INSTANCE;
    private SecretKey key;
    private IvParameterSpec iv;
    AESProperties() {
        try {
            JsonNode configNode = Utils.getConfigNode();
            JsonNode aesNode = configNode.get("aes");
            final String keyString = aesNode.get("key").asText();
            final String salt = aesNode.get("salt").asText();
            final String ivString = aesNode.get("iv").asText();
            key = getKey(keyString, salt);
            iv = generateIv(ivString);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SecretKey getKey() {
        return key;
    }

    public IvParameterSpec getIv() {
        return iv;
    }

    private SecretKey getKey(String password, String salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
        return secret;
    }

    private IvParameterSpec generateIv(String ivString) {
        if(ivString.length() == 16)
            return new IvParameterSpec(ivString.getBytes(StandardCharsets.UTF_8));
        else
            return null;
    }
}
