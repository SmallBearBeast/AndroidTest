package com.example.libmmf.AppVal;

import android.content.Context;
import android.content.SharedPreferences;

public class AppLongVal extends AppVal {
    private long mVal;

    public AppLongVal(String key, long val) {
        if (sApp == null) {
            throw new RuntimeException("should init AppVal first");
        }
        mKey = key;
        mVal = val;
    }

    public long get() {
        int index = 0;
        if (mGroup != -1) {
            return sApp.getSharedPreferences(sBaseFileName + mGroup, Context.MODE_PRIVATE).getLong(mKey, mVal);
        }
        while (index < sSpFileCount.get()) {
            SharedPreferences sp = sApp.getSharedPreferences(sBaseFileName + index, Context.MODE_PRIVATE);
            if (sp.contains(mKey)) {
                mGroup = index;
                return sp.getLong(mKey, mVal);
            }
            index ++;
        }
        return mVal;
    }

    public void set(long val) {
        mVal = val;
        if (mGroup != -1) {
            sApp.getSharedPreferences(sBaseFileName + mGroup, Context.MODE_PRIVATE).edit().putLong(mKey, mVal).apply();
            return;
        }
        int index = 0;
        int insertIndex = -1;
        while (index < sSpFileCount.get()) {
            SharedPreferences sp = sApp.getSharedPreferences(sBaseFileName + index, Context.MODE_PRIVATE);
            if (sp.contains(mKey)) {
                sp.edit().putLong(mKey, mVal).apply();
                mGroup = index;
                return;
            } else if (insertIndex == -1 && sp.getInt(SP_FILE_LENGTH, 0) < sSpLengthLimit) {
                insertIndex = index;
            }
            index++;
        }
        if (insertIndex != -1) {
            SharedPreferences sp = sApp.getSharedPreferences(sBaseFileName + insertIndex, Context.MODE_PRIVATE);
            sp.edit().putInt(SP_FILE_LENGTH, sp.getInt(SP_FILE_LENGTH, 0) + 1).putLong(mKey, mVal).apply();
            mGroup = insertIndex;
            return;
        }
        if (index == sSpFileCount.get()) {
            sSpFileCount.incrementAndGet();
            SharedPreferences.Editor editor = sApp.getSharedPreferences(sBaseFileName + index, Context.MODE_PRIVATE).edit();
            editor.putInt(SP_FILE_LENGTH, 1).putLong(mKey, mVal).apply();
            mGroup = index;
        }
    }

    public void inc() {
        set(mVal + 1);
    }

    public void dec() {
        set(mVal - 1);
    }
}
