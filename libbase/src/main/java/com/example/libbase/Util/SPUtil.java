package com.example.libbase.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringDef;

/**
 * 适用于存储数据量小，不频繁存储的条件下。
 */
public class SPUtil extends AppInitUtil {
    @StringDef({SETTING, OTHER})
    public @interface SpName{

    }
    public static final String SETTING = "SETTING"; //设置
    public static final String OTHER = "OTHER"; //其他

    /**
     * 提前加载sp配置文件
     */
    public static void init(@SpName String... spNames){
        for (int i = 0; i < spNames.length; i++) {
            AppUtil.getApp().getSharedPreferences(spNames[i], Context.MODE_PRIVATE);
        }
    }

    private static void putData(@SpName String spName, String[] key, Object[] value){
        if(!CollectionUtil.isSameLength(key, value)){
            return;
        }
        SharedPreferences.Editor editor = sContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        for (int i = 0, len = key.length; i < len; i++) {
            if(value[i] instanceof Boolean){
                editor.putBoolean(key[i], (Boolean) value[i]);
            }else if(value[i] instanceof Integer){
                editor.putInt(key[i], (Integer) value[i]);
            }else if(value[i] instanceof String){
                editor.putString(key[i], (String) value[i]);
            }else if(value[i] instanceof Float){
                editor.putFloat(key[i], (Float) value[i]);
            }else if(value[i] instanceof Long){
                editor.putLong(key[i], (Long) value[i]);
            }
        }
        editor.apply();
    }

    private static void putData(@SpName String spName, String key, Object value){
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

    public static Object getData(@SpName String spName, String key, Object defaultValue) {
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

    public static void remove(@SpName String spName, String key){
        SharedPreferences.Editor editor = sContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clear(@SpName String spName){
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

    public static Object getDataFromSetting(String key, Object defaultValue){
        return getData(SETTING, key, defaultValue);
    }

    public static void putDataToSetting(String key, Object value){
        putData(SETTING, key, value);
    }
}

