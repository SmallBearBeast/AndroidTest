package com.example.libmmf.MmpVal;

import com.example.libmmf.Storage.MMKVStorage;

public class MmpFloatVal extends MmpVal {
    private float mVal;

    public MmpFloatVal(String key, float val) {
        if (checkInit()) {
            mKey = key;
            mVal = val;
        }
    }

    public float get() {
        return MMKVStorage.getFloat(MMKV_MMPVAL_ID, mKey, mVal);
    }

    public void set(int val) {
        mVal = val;
        MMKVStorage.putFloat(MMKV_MMPVAL_ID, mKey, val);
    }
}
