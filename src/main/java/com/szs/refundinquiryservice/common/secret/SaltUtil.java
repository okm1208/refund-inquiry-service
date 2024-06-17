package com.szs.refundinquiryservice.common.secret;

import java.security.SecureRandom;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
public class SaltUtil {

    public static String generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
