package com.example.administrator.androidtest.Test.MainTest.OptTest.BootOptTest.BootTask;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.administrator.androidtest.AndroidTestApplication;
import com.example.libokhttp.OkHelper;

import okhttp3.OkHttpClient;

public class BasicBgThreadTask extends BaseBootTask {
    public BasicBgThreadTask() {
        super(BootConstant.TASK_TYPE_BASIC_BGTHREAD, true);
    }

    @Override
    protected void run(@NonNull String s) {
        // 网络初始化，so库加载，数据库初始化，文件操作初始化(读取文件配置内容)，sp预加载(内部启一个线程去加载的)
        OkHelper.init((Application) AndroidTestApplication.getContext(), null);
    }
}
