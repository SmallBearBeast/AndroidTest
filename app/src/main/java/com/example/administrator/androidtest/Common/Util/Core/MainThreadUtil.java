package com.example.administrator.androidtest.Common.Util.Core;

import android.os.Handler;
import android.os.Looper;


/***
 * Description:Thread Utils
 * Creator: wangwei7@bigo.sg
 * Date:2017-10-26 02:46:08 PM
 ***/
public class MainThreadUtil {
    public static void run(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            LazyHolder.sHandler.post(r);
        }
    }

    public static void runFirst(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            LazyHolder.sHandler.postAtFrontOfQueue(r);
        }
    }

    public static void run(Runnable r, long delay) {
        LazyHolder.sHandler.postDelayed(r, delay);
    }

    public static void removeCallbacks(Runnable r) {
        LazyHolder.sHandler.removeCallbacks(r);
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private static class LazyHolder {
        private static Handler sHandler = new Handler(Looper.getMainLooper());
    }
}
