package com.example.administrator.androidtest.Common.Util.Core;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadUtil {
    private static Executor mExecutor = Executors.newCachedThreadPool();


    public static void execute(Runnable run){
        mExecutor.execute(run);
    }
}
