package com.bear.librv;

import android.util.Log;

public class RvLog {
    static final String RV_LOG_TAG = "rv_log_tag";

    private static boolean DEBUG = BuildConfig.DEBUG;

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);

        }
    }

    static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }
}
