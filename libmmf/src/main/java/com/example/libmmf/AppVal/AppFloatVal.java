package com.example.libmmf.AppVal;

import android.content.Context;
import android.content.SharedPreferences;

public class AppFloatVal extends AppVal {
    private float mVal;

    public AppFloatVal(String key, float val) {
        if (sApp == null) {
            throw new RuntimeException("should init AppVal first");
        }
        mKey = key;
        mVal = val;
    }

    public float get() {
        int index = 0;
        if (mGroup != -1) {
            return sApp.getSharedPreferences(sBaseFileName + mGroup, Context.MODE_PRIVATE).getFloat(mKey, mVal);
        }
        while (index < sSpFileCount.get()) {
            SharedPreferences sp = sApp.getSharedPreferences(sBaseFileName + index, Context.MODE_PRIVATE);
            if (sp.contains(mKey)) {
                mGroup = index;
                return sp.getFloat(mKey, mVal);
            }
            index ++;
        }
        return mVal;
    }

    public void set(int val) {
        mVal = val;
        if (mGroup != -1) {
            sApp.getSharedPreferences(sBaseFileName + mGroup, Context.MODE_PRIVATE).edit().putFloat(mKey, mVal).apply();
            return;
        }
        int index = 0;
        int insertIndex = -1;
        while (index < sSpFileCount.get()) {
            SharedPreferences sp = sApp.getSharedPreferences(sBaseFileName + index, Context.MODE_PRIVATE);
            if (sp.contains(mKey)) {
                sp.edit().putFloat(mKey, mVal).apply();
                mGroup = index;
                return;
            } else if (insertIndex == -1 && sp.getInt(SP_FILE_LENGTH, 0) < sSpLengthLimit) {
                insertIndex = index;
            }
            index++;
        }
        if (insertIndex != -1) {
            SharedPreferences sp = sApp.getSharedPreferences(sBaseFileName + insertIndex, Context.MODE_PRIVATE);
            sp.edit().putInt(SP_FILE_LENGTH, sp.getInt(SP_FILE_LENGTH, 0) + 1).putFloat(mKey, mVal).apply();
            mGroup = insertIndex;
            return;
        }
        if (index == sSpFileCount.get()) {
            sSpFileCount.incrementAndGet();
            SharedPreferences.Editor editor = sApp.getSharedPreferences(sBaseFileName + index, Context.MODE_PRIVATE).edit();
            editor.putInt(SP_FILE_LENGTH, 1).putFloat(mKey, mVal).apply();
            mGroup = index;
        }
    }
}
