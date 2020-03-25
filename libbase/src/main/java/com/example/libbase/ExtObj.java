package com.example.libbase;

import java.util.HashMap;
import java.util.Map;

public abstract class ExtObj {
    private Map<String, Object> mExtraMap;

    public void put(String key, Object value) {
        if (mExtraMap == null) {
            mExtraMap = new HashMap<>();
        }
        mExtraMap.put(key, value);
    }

    public <V> V get(String key) {
        if (mExtraMap == null) {
            return null;
        }
        Object obj = mExtraMap.get(key);
        return (V) obj;
    }
}
