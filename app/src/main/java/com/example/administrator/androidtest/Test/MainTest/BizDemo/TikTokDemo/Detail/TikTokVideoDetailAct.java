package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.Detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;

public class TikTokVideoDetailAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int layoutId() {
        return 0;
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, TikTokVideoDetailAct.class);
        context.startActivity(intent);
    }
}
