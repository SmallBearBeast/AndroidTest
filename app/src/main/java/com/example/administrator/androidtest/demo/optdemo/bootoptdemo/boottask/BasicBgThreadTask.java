package com.example.administrator.androidtest.demo.optdemo.bootoptdemo.boottask;

import android.app.Application;

import androidx.annotation.NonNull;

import com.bear.libokhttp.OkHelper;
import com.example.administrator.androidtest.AndroidTestApplication;

public class BasicBgThreadTask extends BaseBootTask {
    public BasicBgThreadTask() {
        super(BootConstant.TASK_TYPE_BASIC_BGTHREAD, true);
    }

    @Override
    protected void run(@NonNull String s) {
        // 网络初始化，so库加载，数据库初始化，文件操作初始化(读取文件配置内容)，sp预加载(内部启一个线程去加载的)
        OkHelper.init((Application) AndroidTestApplication.context, null);
    }
}
