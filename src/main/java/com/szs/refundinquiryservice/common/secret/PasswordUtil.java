package com.szs.refundinquiryservice.common.secret;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
public class PasswordUtil {

    public static String createHash(String password, String salt) {
        return createHash(password.toCharArray(), salt);
    }

    public static String createHash(char[] password, String salt) {
        byte[] hash = pbkdf2(password, salt.getBytes(), 1000, 16);
        return "1000:" + SaltUtil.bytesToHex(salt.getBytes()) + ":" + SaltUtil.bytesToHex(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException var6) {
            var6.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public static boolean validatePassword(String password, String correctHash) {
        return validatePassword(password.toCharArray(), correctHash);
    }

    public static boolean validatePassword(char[] password, String correctHash) {
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];

        for (int i = 0; i < binary.length; ++i) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        return binary;

    }


    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;

        for (int i = 0; i < a.length && i < b.length; ++i) {
            diff |= a[i] ^ b[i];
        }

        return diff == 0;
    }

}


