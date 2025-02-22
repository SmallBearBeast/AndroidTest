package com.example.administrator.androidtest.demo.BizDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TikTokDemoComponent;

public class BizDemoAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new TikTokDemoComponent(getLifecycle()));
    }

    @Override
    protected int layoutId() {
        return R.layout.act_biz_demo_list;
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, BizDemoAct.class);
        context.startActivity(intent);
    }
}
