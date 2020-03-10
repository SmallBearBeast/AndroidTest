package com.example.libmmf.MmpVal;

import com.example.libmmf.Storage.MMKVStorage;

public class MmpStringVal extends MmpVal {
    private String mVal;

    public MmpStringVal(String key, String val) {
        if (checkInit()) {
            mKey = key;
            mVal = val;
        }
    }

    public String get() {
        return MMKVStorage.getString(MMKV_MMPVAL_ID, mKey, mVal);
    }

    public void set(String val) {
        mVal = val;
        MMKVStorage.putString(MMKV_MMPVAL_ID, mKey, val);
    }
}
