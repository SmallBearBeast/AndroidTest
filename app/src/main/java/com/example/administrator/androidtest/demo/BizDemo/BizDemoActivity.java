package com.example.administrator.androidtest.demo.BizDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcomponent.host.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActBizDemoListBinding;
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TikTokDemoComponent;

public class BizDemoActivity extends ComponentActivity<ActBizDemoListBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regComponent(new TikTokDemoComponent(getLifecycle()));
    }

    @Override
    protected ActBizDemoListBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActBizDemoListBinding.inflate(inflater);
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, BizDemoActivity.class);
        context.startActivity(intent);
    }
}
