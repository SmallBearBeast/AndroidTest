package com.example.administrator.androidtest.demo.MediaDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActMediaDemoListBinding;
import com.example.administrator.androidtest.demo.MediaDemo.PlayerDemo.PlayerDemoComponent;

public class MediaDemoActivity extends ComponentActivity<ActMediaDemoListBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new PlayerDemoComponent(getLifecycle()));
    }

    @Override
    protected ActMediaDemoListBinding inflateViewBinding(LayoutInflater inflater) {
        return ActMediaDemoListBinding.inflate(inflater);
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, MediaDemoActivity.class);
        context.startActivity(intent);
    }
}

