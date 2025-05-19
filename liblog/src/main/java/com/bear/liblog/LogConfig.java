package com.bear.liblog;

import android.util.Log;

public class LogConfig {
    public static final int VERBOSE = Log.VERBOSE;
    public static final int DEBUG = Log.DEBUG;
    public static final int INFO = Log.INFO;
    public static final int WARN = Log.WARN;
    public static final int ERROR = Log.ERROR;
    public static final int ASSERT = Log.ASSERT;

    private @LogLevel int mLogLevel;
    private String mLogSavePath;

    public int getLogLevel() {
        return mLogLevel;
    }

    public void setLogLevel(int logLevel) {
        mLogLevel = logLevel;
    }

    public String getLogSavePath() {
        return mLogSavePath;
    }

    public void setLogSavePath(String logSavePath) {
        mLogSavePath = logSavePath;
    }
}
