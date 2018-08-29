package com.example.administrator.androidtest.Common.Util;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by Administrator on 2017/7/10.
 */

public class SPUtil {
    public static final String SETTING = "SETTING";

    /**
     * 将数据保存到 IM 文件中
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveToIM(Context context, String key, Object value) {
        SharedPreferences preferences = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        putData(preferences, key, value);
    }

    private static void putData(SharedPreferences preferences,  String key, Object value){
        SharedPreferences.Editor editor = preferences.edit();
        if(value instanceof Boolean){
            editor.putBoolean(key, (Boolean) value);
        }else if(value instanceof Integer){
            editor.putInt(key, (Integer) value);
        }else if(value instanceof String){
            editor.putString(key, (String) value);
        }else if(value instanceof Float){
            editor.putFloat(key, (Float) value);
        }else if(value instanceof Long){
            editor.putLong(key, (Long) value);
        }
        editor.commit();
    }
    /**
     * 从 IM 文件中 取出数据
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object getFromIM(Context context, String key, Object defaultValue) {
        String type = defaultValue.getClass().getSimpleName();
        SharedPreferences preferences = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        Object result = null;
        switch (type) {
            case "Boolean":
                result = preferences.getBoolean(key, (Boolean) defaultValue);
                break;

            case "Integer":
                result = preferences.getInt(key, (Integer) defaultValue);
                break;

            case "String":
                result = preferences.getString(key, (String) defaultValue);
                break;
        }
        return result;
    }
}

