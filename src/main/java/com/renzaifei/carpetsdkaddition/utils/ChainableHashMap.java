package com.renzaifei.carpetsdkaddition.utils;

import java.util.HashMap;

public class ChainableHashMap<K, V> extends HashMap<K, V> {
    @SuppressWarnings("UnusedReturnValue")
    public ChainableHashMap<K, V> cPut(K key, V value) {
        super.put(key, value);
        return this;
    }
}
