package com.example.libcommon.Executor;

import android.os.Handler;
import android.os.Looper;
import android.util.Pair;

public class MainThreadExecutor {
    public static void post(Runnable r) {
        LazyHolder.sHandler.post(r);
    }

    public static void postAtFrontOfQueue(Runnable r) {
        LazyHolder.sHandler.postAtFrontOfQueue(r);
    }

    public static void postDelayed(Runnable r, long delay) {
        LazyHolder.sHandler.postDelayed(r, delay);
    }

    public static void postDelayed(Pair<Runnable, Long>... pairs) {
        Pair<Runnable, Long> pair;
        for (Pair<Runnable, Long> runnableLongPair : pairs) {
            pair = runnableLongPair;
            LazyHolder.sHandler.postDelayed(pair.first, pair.second);
        }
    }

    public static void removeCallbacks(Runnable... rs) {
        for (Runnable r : rs) {
            LazyHolder.sHandler.removeCallbacks(r);
        }
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private static class LazyHolder {
        private static Handler sHandler = new Handler(Looper.getMainLooper());
    }
}
