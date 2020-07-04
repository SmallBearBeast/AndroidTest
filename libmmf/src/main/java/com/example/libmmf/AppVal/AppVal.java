package com.example.libmmf.AppVal;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

public abstract class AppVal {
    private static final String DEFAULT_NAME = "default_name";
    private static boolean sIsInit = false;
    private static final HashSet<String> sSpNameSet = new HashSet<>();
    private static Application sApp;
    private String mSpName;
    private String mKey;

    AppVal(String key) {
        this(DEFAULT_NAME, key);
    }

    AppVal(String spName, String key) {
        checkInit();
        checkSpName(spName);
        mSpName = spName;
        mKey = key;
    }

    public static void init(Application app, String... spNames) {
        sIsInit = true;
        sApp = app;
        for (String spName : spNames) {
            sApp.getSharedPreferences(spName, Context.MODE_PRIVATE);
            sSpNameSet.add(spName);
        }
        sSpNameSet.add(DEFAULT_NAME);
    }

    public static void clear(String... spNames) {
        for (String spName : spNames) {
            if (sSpNameSet.contains(spName)) {
                sApp.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().clear().apply();
            }
        }
    }

    public static void clearAll() {
        for (String spName : sSpNameSet) {
            sApp.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().clear().apply();
        }
    }

    static void checkInit() {
        if (!sIsInit) {
            throw new RuntimeException("should init MmpVal first");
        }
    }

    static void checkSpName(String spName) {
        if (!sSpNameSet.contains(spName)) {
            throw new RuntimeException("spName should be contained in sSpNameSet");
        }
    }

    SharedPreferences getSp() {
        return sApp.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
    }

    SharedPreferences.Editor getEditor() {
        return sApp.getSharedPreferences(mSpName, Context.MODE_PRIVATE).edit();
    }

    String getKey() {
        return mKey;
    }
}
