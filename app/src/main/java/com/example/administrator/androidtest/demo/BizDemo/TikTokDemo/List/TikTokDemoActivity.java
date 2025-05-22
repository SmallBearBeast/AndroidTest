package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActTiktokDemoBinding;

public class TikTokDemoActivity extends ComponentActivity<ActTiktokDemoBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new TiktokListComponent(getLifecycle()));
    }

    @Override
    protected ActTiktokDemoBinding inflateViewBinding(LayoutInflater inflater) {
        return ActTiktokDemoBinding.inflate(inflater);
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, TikTokDemoActivity.class);
        context.startActivity(intent);
    }
}
