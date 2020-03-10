package com.example.libmmf.MmpVal;

import com.example.libmmf.Storage.MMKVStorage;

public class MmpIntVal extends MmpVal {
    private int mVal;

    public MmpIntVal(String key, int val) {
        if (checkInit()) {
            mKey = key;
            mVal = val;
        }
    }

    public int get() {
        return MMKVStorage.getInt(MMKV_MMPVAL_ID, mKey, mVal);
    }

    public void set(int val) {
        mVal = val;
        MMKVStorage.putInt(MMKV_MMPVAL_ID, mKey, val);
    }

    public void inc() {
        set(mVal + 1);
    }

    public void dec() {
        set(mVal - 1);
    }
}
