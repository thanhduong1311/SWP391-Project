package com.demo.homemate.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordMD5 {
    public static String encode(String password) throws NoSuchAlgorithmException, NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(password.getBytes());
        byte[] digest = md5.digest();

        return toHexString(digest);
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
