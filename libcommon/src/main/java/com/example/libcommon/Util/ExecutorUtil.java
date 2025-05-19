package com.example.libcommon.Util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorUtil {
    private static final Executor mExecutor = Executors.newCachedThreadPool();

    public static void execute(Runnable run){
        mExecutor.execute(run);
    }
}
