package com.example.administrator.androidtest.demo.OptTest.BootOptTest.BootTask;

import androidx.annotation.NonNull;

import com.effective.android.anchors.task.Task;

public abstract class BaseBootTask extends Task {

    protected final String TAG = this.getClass().getSimpleName();

    public BaseBootTask(@NonNull String id, boolean isAsyncTask) {
        super(id, isAsyncTask);
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
