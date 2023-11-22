package com.example.administrator.androidtest.Test.MainTest.OptTest.BootOptTest.BootTask;

import androidx.annotation.NonNull;

public class NoAnchorDPTask extends BaseBootTask {
    public NoAnchorDPTask() {
        super(BootConstant.TASK_TYPE_NOANCHORDP, true);
        setPriority(BootConstant.PRIORITY_LOW);
    }

    @Override
    protected void run(@NonNull String s) {
        // 同步数据任务，上传任务，其他一些启动执行任务。
    }
}
