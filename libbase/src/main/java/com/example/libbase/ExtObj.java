package com.example.libbase;

import java.util.HashMap;
import java.util.Map;

public abstract class ExtObj {
    private Map<String, Object> otherAttr;

    public void put(String key, Object value) {
        if (otherAttr == null) {
            otherAttr = new HashMap<>();
        }
        otherAttr.put(key, value);
    }

    public <V> V get(String key) {
        if (otherAttr == null) {
            return null;
        }
        Object obj = otherAttr.get(key);
        return (V) obj;
    }
}
