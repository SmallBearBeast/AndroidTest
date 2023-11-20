package com.example.administrator.androidtest.Test.MainTest.BootOptTest.BootTask;

import android.util.Log;

import androidx.annotation.NonNull;

public class BlockTask extends BaseBootTask {

    public BlockTask() {
        super("BlockTask", false);
    }

    @Override
    protected void run(@NonNull String s) {
        long costTime = 1000L;
        sleep(costTime);
        Log.d(TAG, "run: BlockTask cost " + costTime + "ms");
    }
}
