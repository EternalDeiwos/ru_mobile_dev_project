package com.github.eternaldeiwos.biomapapp.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by glinklater on 2016/05/27.
 */
public class Hash {
    private Hash() {}

    public static String md5Password(String plain_text) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(plain_text.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
