package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo;

import android.content.Context;
import android.content.Intent;

import com.bear.libcomponent.component.ComponentAct;

public class TikTokDemoAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return 0;
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, TikTokDemoAct.class);
        context.startActivity(intent);
    }
}
