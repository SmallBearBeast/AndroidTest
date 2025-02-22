package com.example.administrator.androidtest.other;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class LogUtil {
    private String logTag = "";
    private String logName = "";
    private final Map<String, Object> logContentMap = new HashMap<>();

    private static class SingleTon {
        private static final LogUtil Instance = new LogUtil();
    }

    public static LogUtil getInstance(String tag, String name) {
        SingleTon.Instance.tag(tag);
        SingleTon.Instance.name(name);
        return SingleTon.Instance;
    }

    public LogUtil tag(String tag) {
        logTag = tag;
        return this;
    }

    public LogUtil name(String name) {
        logName = name;
        return this;
    }

    public LogUtil of(String key, Object val) {
        logContentMap.put(key, val);
        return this;
    }

    public void logV() {
        Log.v(logTag, logName + ": " + getLogContent());
    }

    public void logD() {
        Log.d(logTag, logName + ": " + getLogContent());
    }

    public void logI() {
        Log.i(logTag, logName + ": " + getLogContent());
    }

    public void logW() {
        Log.w(logTag, logName + ": " + getLogContent());
    }

    public void logE() {
        Log.e(logTag, logName + ": " + getLogContent());
    }

    private String getLogContent() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : logContentMap.entrySet()) {
            builder.append(entry.getKey());
            builder.append(" = ");
            builder.append(entry.getValue());
            builder.append(", ");
        }
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 2);
        }
        return builder.toString();
    }

    private void clear() {
        logTag = "";
        logName = "";
        logContentMap.clear();
    }
}
