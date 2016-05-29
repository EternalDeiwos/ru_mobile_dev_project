package com.github.eternaldeiwos.biomapapp.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by glinklater on 2016/05/27.
 */
public final class HashHelper {
    private HashHelper() {} // static class

    public static String hashMD5(String plain_text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return new String(md.digest(plain_text.getBytes()));
    }

    public static String hashSHA1(String plain_text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return new String(md.digest(plain_text.getBytes()));
    }
}
