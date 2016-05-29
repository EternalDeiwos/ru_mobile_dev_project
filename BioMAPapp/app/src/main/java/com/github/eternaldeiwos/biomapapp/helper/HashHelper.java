package com.github.eternaldeiwos.biomapapp.helper;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by glinklater on 2016/05/27.
 */
public final class HashHelper {
    private HashHelper() {} // static class
    private static HashMap<String, String> hashes; // replace with thread-safe persistence later

    public static String hashMD5(String plain_text) {
        HashCode hc = Hashing.md5().hashString(plain_text, Charset.defaultCharset());
        String hashed = hc.toString();
        System.err.println(hashed);
        return hashed;
    }

    public static String hashSHA1(String plain_text) {
        HashCode hc = Hashing.sha1().hashString(plain_text, Charset.defaultCharset());
        return hc.toString();
    }

    private static Map<String, String> getHashes() {
        if (hashes == null) hashes = new HashMap<>();
        return hashes;
    }

    public static boolean isDataChanged(String key, String data) {
        if (getHashes().containsKey(key))
            if (getHashes().get(key).equals(hashSHA1(data))) return false;
        return true;
    }

    public static void updateHash (String key, String data) {
        getHashes().put(key, hashSHA1(data));
    }

    public static String getHash (String key) {
        return getHashes().get(key);
    }
}
