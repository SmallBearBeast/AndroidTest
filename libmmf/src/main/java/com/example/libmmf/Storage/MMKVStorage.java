package com.example.libmmf.Storage;

import android.app.Application;

import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVHandler;
import com.tencent.mmkv.MMKVLogLevel;
import com.tencent.mmkv.MMKVRecoverStrategic;

import java.util.Set;

// https://github.com/Tencent/MMKV/wiki/android_advance
public class MMKVStorage {
    public static void init(Application app) {
        MMKV.initialize(app);
    }

    public static void putInt(String mmkvId, String key, int val) {
        MMKV.mmkvWithID(mmkvId).putInt(key, val);
    }

    public static void putBoolean(String mmkvId, String key, boolean val) {
        MMKV.mmkvWithID(mmkvId).putBoolean(key, val);
    }

    public static void putBytes(String mmkvId, String key, byte[] val) {
        MMKV.mmkvWithID(mmkvId).putBytes(key, val);
    }

    public static void putFloat(String mmkvId, String key, float val) {
        MMKV.mmkvWithID(mmkvId).putFloat(key, val);
    }

    public static void putLong(String mmkvId, String key, long val) {
        MMKV.mmkvWithID(mmkvId).putLong(key, val);
    }

    public static void putString(String mmkvId, String key, String val) {
        MMKV.mmkvWithID(mmkvId).putString(key, val);
    }

    public static void putStringSet(String mmkvId, String key, Set<String> val) {
        MMKV.mmkvWithID(mmkvId).putStringSet(key, val);
    }

    public static boolean getBoolean(String mmkvId, String key, boolean defaultVal) {
        return MMKV.mmkvWithID(mmkvId).getBoolean(key, defaultVal);
    }

    public static byte[] getBytes(String mmkvId, String key, byte[] defaultVal) {
        return MMKV.mmkvWithID(mmkvId).getBytes(key, defaultVal);
    }

    public static float getFloat(String mmkvId, String key, float defaultVal) {
        return MMKV.mmkvWithID(mmkvId).getFloat(key, defaultVal);
    }

    public static int getInt(String mmkvId, String key, int defaultVal) {
        return MMKV.mmkvWithID(mmkvId).getInt(key, defaultVal);
    }

    public static long getLong(String mmkvId, String key, long defaultVal) {
        return MMKV.mmkvWithID(mmkvId).getLong(key, defaultVal);
    }

    public static String getString(String mmkvId, String key, String defaultVal) {
        return MMKV.mmkvWithID(mmkvId).getString(key, defaultVal);
    }

    public static Set<String> getStringSet(String mmkvId, String key, Set<String> defaultVal) {
        return MMKV.mmkvWithID(mmkvId).getStringSet(key, defaultVal);
    }

    public static void remove(String mmkvId, String... keys) {
        MMKV.mmkvWithID(mmkvId).removeValuesForKeys(keys);
    }

    public static void clear(String mmkvId) {
        MMKV.mmkvWithID(mmkvId).clearAll();
    }

    private static class LogMMKVHandler implements MMKVHandler {
        @Override
        public MMKVRecoverStrategic onMMKVCRCCheckFail(String s) {
            return MMKVRecoverStrategic.OnErrorRecover;
        }

        @Override
        public MMKVRecoverStrategic onMMKVFileLengthError(String s) {
            return MMKVRecoverStrategic.OnErrorRecover;
        }

        @Override
        public boolean wantLogRedirecting() {
            return false;
        }

        @Override
        public void mmkvLog(MMKVLogLevel mmkvLogLevel, String s, int i, String s1, String s2) {

        }
    }
}
