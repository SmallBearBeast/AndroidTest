package com.example.libbase.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class is suitable for storing small variables equivalent to phone-wide variables.
 */
public class SPUtil extends AppInitUtil {

    /**
     * Initialize sp file in advance.
     *
     * @param spNames sp file array.
     */
    public static void preInit(String... spNames) {
        for (int i = 0; i < spNames.length; i++) {
            getContext().getSharedPreferences(spNames[i], Context.MODE_PRIVATE);
        }
    }

    public static void put(String spName, String[] keys, Object[] values) {
        boolean check = (keys != null && values != null && keys.length == values.length);
        if (!check) {
            return;
        }
        SharedPreferences.Editor editor = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        for (int i = 0, len = keys.length; i < len; i++) {
            put(spName, keys[i], values[i]);
        }
        editor.apply();
    }

    public static void put(String spName, String key, Object value) {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.apply();
    }

    public static <T> T get(String spName, String key, T defaultValue) {
        SharedPreferences preferences = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        Object result = null;
        if (defaultValue instanceof Boolean) {
            result = preferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Integer) {
            result = preferences.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof String) {
            result = preferences.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Float) {
            result = preferences.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            result = preferences.getLong(key, (Long) defaultValue);
        }
        return (T) result;
    }

    public static void remove(String spName, String key) {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clear(String spName) {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}

