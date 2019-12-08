package com.example.libframework.Rv;

import java.util.HashMap;
import java.util.Map;

public class Notify {
    public int mType;

    public Map<String, Object> mValue;

    public Notify(int type) {
        mType = type;
    }

    public Notify with(String key, Object value) {
        if (mValue == null) {
            mValue = new HashMap<>(4);
        }
        mValue.put(key, value);
        return this;
    }
}
