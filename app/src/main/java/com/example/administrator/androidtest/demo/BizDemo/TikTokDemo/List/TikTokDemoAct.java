package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;

public class TikTokDemoAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new TiktokListComponent(getLifecycle()));
    }

    @Override
    protected int layoutId() {
        return R.layout.act_tiktok_demo;
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, TikTokDemoAct.class);
        context.startActivity(intent);
    }
}
