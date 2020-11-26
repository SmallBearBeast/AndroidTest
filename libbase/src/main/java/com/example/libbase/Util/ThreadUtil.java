package com.example.libbase.Util;

import android.os.Handler;
import android.os.Looper;
import android.util.Pair;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadUtil {
    private static Executor mExecutor = Executors.newCachedThreadPool();

    public static void execute(Runnable run){
        mExecutor.execute(run);
    }

    public static void postOnMain(Runnable r) {
        ThreadUtil.LazyHolder.sHandler.post(r);
    }

    public static void postOnMainFront(Runnable r) {
        ThreadUtil.LazyHolder.sHandler.postAtFrontOfQueue(r);
    }

    public static void postOnMain(Runnable r, long delay) {
        ThreadUtil.LazyHolder.sHandler.postDelayed(r, delay);
    }

    public static void postOnMain(Pair<Runnable, Long>... pairs){
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
