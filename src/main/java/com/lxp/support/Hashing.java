package com.lxp.support;

import com.lxp.global.exception.LXPException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class Hashing {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;
    private static final String DELIMITER = ":";

    public static String encode(String password) throws Exception {
        byte[] salt = salt();
        byte[] hash = hash(password.toCharArray(), salt);

        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encodedHash = Base64.getEncoder().encodeToString(hash);

        return encodedSalt + DELIMITER + encodedHash;
    }

    public static boolean matches(String inputPassword, String storedHashString) throws Exception {
        String[] parts = divide(storedHashString);
        String encodedSalt = parts[0];
        String encodedHash = parts[1];

        byte[] salt = Base64.getDecoder().decode(encodedSalt);
        byte[] inputHash = hash(inputPassword.toCharArray(), salt);
        String encodedInputHash = Base64.getEncoder().encodeToString(inputHash);

        return encodedInputHash.equals(encodedHash);
    }

    private static byte[] hash(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return SecretKeyFactory.getInstance(ALGORITHM)
            .generateSecret(new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH))
            .getEncoded();
    }

    private static byte[] salt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private static String[] divide(String s) {
        String[] parts = s.split(DELIMITER);
        if (parts.length != 2) {
            throw new LXPException("저장된 해시 문자열 형식이 잘못되었습니다.");
        }
        return parts;
    }
}
