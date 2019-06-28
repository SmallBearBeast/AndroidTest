package com.example.administrator.androidtest.Log;

public class SLog{
    private static LogConfig mConfig = new LogConfig();

    private static ILog sLog = new DefaultLog();

    public static void i(String tag, String msg) {
        sLog.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        sLog.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        sLog.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        sLog.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        sLog.w(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        sLog.i(tag, msg, tr);
    }

    public static void d(String tag, String msg, Throwable tr) {
        sLog.d(tag, msg, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        sLog.e(tag, msg, tr);
    }

    public static void v(String tag, String msg, Throwable tr) {
        sLog.v(tag, msg, tr);
    }

    public static void w(String tag, String msg, Throwable tr) {
        sLog.w(tag, msg, tr);
    }
}
