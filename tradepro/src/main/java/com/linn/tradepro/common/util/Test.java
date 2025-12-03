package com.linn.tradepro.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class Test {
    public static void main(String[] args) {
        SimpleHash simplehash = new SimpleHash("SHA-256", "123456", "linn");
        System.out.println(simplehash);
    }
}
