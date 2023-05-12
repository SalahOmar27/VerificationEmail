package com.salah.emailVerfication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomPasswordEncoder extends BCryptPasswordEncoder {

    private final String secretKey;

    public CustomPasswordEncoder(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String decryptedPassword = decrypt(encodedPassword);
        return super.matches(rawPassword, decryptedPassword);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String encryptedPassword = super.encode(rawPassword);
        return encrypt(encryptedPassword);
    }

    private String decrypt(String encryptedPassword) {
        // TODO: Implement decryption logic using secretKey
        return encryptedPassword;
    }

    private String encrypt(String decryptedPassword) {
        // TODO: Implement encryption logic using secretKey
        return decryptedPassword;
    }
}

