package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActTiktokVideoDetailBinding;

public class TikTokVideoDetailActivity extends ComponentActivity<ActTiktokVideoDetailBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new VideoPlayComponent(getLifecycle()));
        regActComponent(new AdapterComponent(getLifecycle()));
    }

    @Override
    protected ActTiktokVideoDetailBinding inflateViewBinding(LayoutInflater inflater) {
        return ActTiktokVideoDetailBinding.inflate(inflater);
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, TikTokVideoDetailActivity.class);
        context.startActivity(intent);
    }
}
