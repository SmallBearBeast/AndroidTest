package com.example.libmmf.MmpVal;

import android.app.Application;

import com.example.libmmf.Storage.MMKVStorage;

class MmpVal {
    private static boolean sIsInit = false;
    static final String MMKV_MMPVAL_ID = "mmkv_mmpval_id";

    String mKey;

    private static void init(Application app) {
        MMKVStorage.init(app);
        sIsInit = true;
    }

    static boolean checkInit() {
        if (!sIsInit) {
            throw new RuntimeException("should init MmpVal first");
        }
        return sIsInit;
    }
}
