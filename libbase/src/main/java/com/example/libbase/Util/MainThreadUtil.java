package com.example.libbase.Util;

import android.os.Handler;
import android.os.Looper;
import android.util.Pair;

public class MainThreadUtil {
    public static void run(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            LazyHolder.sHandler.post(r);
        }
    }

    public static void runFront(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            LazyHolder.sHandler.postAtFrontOfQueue(r);
        }
    }

    public static void run(Runnable r, long delay) {
        LazyHolder.sHandler.postDelayed(r, delay);
    }

    public static void run(Pair<Runnable, Long>... pairs){
        Pair<Runnable, Long> pair;
        for (int i = 0, len = pairs.length; i < len; i++) {
            pair = pairs[i];
            LazyHolder.sHandler.postDelayed(pair.first, pair.second);
        }
    }

    public static void removeCallbacks(Runnable... rs) {
        for (int i = 0, len = rs.length; i < len; i++) {
            LazyHolder.sHandler.removeCallbacks(rs[i]);
        }
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private static class LazyHolder {
        private static Handler sHandler = new Handler(Looper.getMainLooper());
    }
}
