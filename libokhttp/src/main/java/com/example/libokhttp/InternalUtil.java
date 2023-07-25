package com.example.libokhttp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
class InternalUtil {
    private static Application sApp;

    private static final String SP_NAME = "OK_HTTP";

    static void init(Application app) {
        sApp = app;
    }

    private static class LazyHolder {
        private static final Handler sHandler = new Handler(Looper.getMainLooper());
    }

    static void run(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            LazyHolder.sHandler.post(r);
        }
    }

    private static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    static <T> T toObj(String json, TypeToken<T> token){
        if(token.getType() == String.class) {
            return (T) json;
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(json, token.getType());
    }

    static String toJson(Object jsonObj){
        return new GsonBuilder().serializeNulls().create().toJson(jsonObj);
    }

    static boolean hasSpace(long contentLength) {
        boolean hasSpace = true;
        if (isExternalStorageExists()) {
            if (contentLength > getExternalRemainSpace())
                hasSpace = false;
        } else {
            if (contentLength > getInternalSpace())
                hasSpace = false;
        }
        return hasSpace;
    }

    private static boolean isExternalStorageExists() {
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }

    private static long getExternalRemainSpace() {
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    private static long getInternalSpace() {
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            return stat.getBlockSizeLong() * stat.getBlockCountLong();
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1L;
    }

    private static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    static void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            close(closeable);
        }
    }

    static void putData(String key, Object value){
        SharedPreferences.Editor editor = sApp.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
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

    static Object getData(String key, Object defaultValue) {
        SharedPreferences preferences = sApp.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
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

    static void remove(String key){
        SharedPreferences.Editor editor = sApp.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    static boolean createFile(String filePath) {
        if (!checkPath(filePath)) {
            return false;
        }
        return createFile(new File(filePath));
    }

    static boolean createFile(File file) {
        if (file == null) {
            return false;
        }
        return createFile(file.getParent(), file.getName());
    }

    static boolean createFile(String dirPath, String fileName) {
        if (!checkPath(dirPath) || !checkPath(fileName)) {
            return false;
        }
        if (createDir(dirPath)) {
            File file = new File(dirPath, fileName);
            if (!file.exists()) {
                try {
                    return file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    static boolean createDir(String dirPath) {
        if (!checkPath(dirPath)) {
            return false;
        }
        File dirFile = new File(dirPath);
        return createDir(dirFile);
    }

    static boolean createDir(File dirFile) {
        if (dirFile == null) {
            return false;
        }
        if (!dirFile.exists() && !dirFile.mkdirs()) {
            return false;
        }
        return true;
    }

    private static boolean checkPath(String path) {
        if (path == null || path.trim().equals("")) {
            return false;
        }
        return true;
    }

    static <E> Set<E> createConcurrentSet() {
        return Collections.newSetFromMap(new ConcurrentHashMap<E, Boolean>());
    }
}
