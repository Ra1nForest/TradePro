package com.linn.tradepro.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordHash {
    public static String hash(String password) {
        SimpleHash simplehash = new SimpleHash("SHA-256", password, "linn");
        return simplehash.toString();
    }
}
