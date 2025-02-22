package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;

public class TikTokVideoDetailAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new VideoPlayComponent(getLifecycle()));
        regActComponent(new AdapterComponent(getLifecycle()));
    }

    @Override
    protected int layoutId() {
        return R.layout.act_tiktok_video_detail;
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, TikTokVideoDetailAct.class);
        context.startActivity(intent);
    }
}
