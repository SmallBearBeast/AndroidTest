package com.example.administrator.androidtest.Common.Util.Core;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.androidtest.Common.Util.AppInitUtil;

/**
 * Created by Administrator on 2017/7/10.
 */

public class SPUtil extends AppInitUtil {
    public static final String SETTING = "SETTING"; //设置
    public static final String OTHER = "OTHER"; //其他

    // TODO: 2018/12/20 支持多个key-value对添加 
    private static void putData(String spName, String key, Object value){
        SharedPreferences.Editor editor = sContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
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

    public static Object getData(String spName, String key, Object defaultValue) {
        SharedPreferences preferences = sContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
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

    public static void remove(String spName, String key){
        SharedPreferences.Editor editor = sContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clear(String spName){
        SharedPreferences.Editor editor = sContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    public static Object getDataFromOther(String key, Object defaultValue){
        return getData(OTHER, key, defaultValue);
    }

    public static void putDataToOther(String key, Object value){
        putData(OTHER, key, value);
    }
}

