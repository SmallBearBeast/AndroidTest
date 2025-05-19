package com.bear.libcommon.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BgThreadExecutor {
    private static final Executor executor = Executors.newCachedThreadPool();

    public static void execute(Runnable run){
        executor.execute(run);
    }
}