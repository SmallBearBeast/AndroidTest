package com.example.libbase.Executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BgThreadExecutor {
    private static Executor mExecutor = Executors.newCachedThreadPool();

    public static void execute(Runnable run){
        mExecutor.execute(run);
    }
}
