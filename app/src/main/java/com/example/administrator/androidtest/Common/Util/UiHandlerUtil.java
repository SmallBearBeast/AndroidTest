package com.example.administrator.androidtest.Common.Util;

import android.os.Handler;
import android.os.Looper;


/***
 * Description:Thread Utils
 * Creator: wangwei7@bigo.sg
 * Date:2017-10-26 02:46:08 PM
 ***/
public class UiHandlerUtil {
    public static void runOnUiThread(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            LazyHolder.sUiThreadHandler.post(r);
        }
    }

    public static void runOnUiThreadAtFront(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            LazyHolder.sUiThreadHandler.postAtFrontOfQueue(r);
        }
    }

    public static void runOnUiThread(Runnable r, long delay) {
        LazyHolder.sUiThreadHandler.postDelayed(r, delay);
    }

    public static void removeCallbacks(Runnable r) {
        LazyHolder.sUiThreadHandler.removeCallbacks(r);
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private static class LazyHolder {
        private static Handler sUiThreadHandler = new Handler(Looper.getMainLooper());
    }
}
