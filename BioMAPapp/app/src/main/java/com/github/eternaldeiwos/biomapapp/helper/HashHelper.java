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
    private static HashMap<String, String> hashes; // replace with thread-safe persistence later

    public static String hashMD5(String plain_text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return new String(md.digest(plain_text.getBytes()));
    }

    public static String hashSHA1(String plain_text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return new String(md.digest(plain_text.getBytes()));
    }

    private static Map<String, String> getHashes() {
        if (hashes == null) hashes = new HashMap<>();
        return hashes;
    }

    public static boolean isDataChanged(String key, String data) throws NoSuchAlgorithmException {
        if (getHashes().containsKey(key))
            if (getHashes().get(key).equals(hashSHA1(data))) return false;
        return true;
    }

    public static void updateHash (String key, String data) throws NoSuchAlgorithmException {
        getHashes().put(key, hashSHA1(data));
    }

    public static String getHash (String key) {
        return getHashes().get(key);
    }
}
