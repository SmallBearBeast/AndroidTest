package com.bear.librv;

import java.util.HashMap;
import java.util.Map;

public class Payload {
    public int mType;

    public Map<String, Object> mValue;

    public Payload(int type) {
        mType = type;
    }

    public Payload with(String key, Object value) {
        if (mValue == null) {
            mValue = new HashMap<>();
        }
        mValue.put(key, value);
        return this;
    }
}
