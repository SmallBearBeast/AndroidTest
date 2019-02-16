package com.example.administrator.androidtest.Common.Util.Core;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by Administrator on 2017/7/10.
 */

public class SPUtil {
    public static final String SETTING = "SETTING";


    public static void toSetting(Context context, String key, Object value) {
        putData(SETTING, context, key, value);
    }

    public static Object fromSetting(Context context, String key, Object defaultValue){
        return getData(SETTING, context, key, defaultValue);
    }

    // TODO: 2018/12/20 支持多个key-value对添加 
    private static void putData(String spName, Context context, String key, Object value){
        SharedPreferences.Editor editor = context.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
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
        editor.apply();
    }

    public static Object getData(String spName, Context context, String key, Object defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String type = defaultValue.getClass().getSimpleName();
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

