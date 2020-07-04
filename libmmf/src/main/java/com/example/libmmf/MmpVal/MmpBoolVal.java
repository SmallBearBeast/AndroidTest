package com.example.libmmf.MmpVal;

import com.example.libmmf.Storage.MMKVStorage;

public class MmpBoolVal extends MmpVal {
    private boolean mVal;

    public MmpBoolVal(String key, boolean val) {
        if (checkInit()) {
            mKey = key;
            mVal = val;
        }
    }

    public boolean get() {
            return MMKVStorage.getBoolean(MMKV_MMPVAL_ID, mKey, mVal);
    }

    public void set(boolean val) {
        mVal = val;
        MMKVStorage.putBoolean(MMKV_MMPVAL_ID, mKey, val);
    }

    public void reverse() {
        set(!mVal);
    }
}
