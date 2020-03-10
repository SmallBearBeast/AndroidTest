package com.example.libmmf.MmpVal;

import com.example.libmmf.Storage.MMKVStorage;

public class MmpLongVal extends MmpVal {
    private long mVal;

    public MmpLongVal(String key, long val) {
        if (checkInit()) {
            mKey = key;
            mVal = val;
        }
    }

    public long get() {
        return MMKVStorage.getLong(MMKV_MMPVAL_ID, mKey, mVal);
    }

    public void set(long val) {
        mVal = val;
        MMKVStorage.putLong(MMKV_MMPVAL_ID, mKey, val);
    }

    public void inc() {
        set(mVal + 1);
    }

    public void dec() {
        set(mVal - 1);
    }
}
