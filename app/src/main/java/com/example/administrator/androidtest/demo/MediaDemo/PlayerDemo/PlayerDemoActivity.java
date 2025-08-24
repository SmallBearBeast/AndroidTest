package com.example.administrator.androidtest.demo.MediaDemo.PlayerDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcomponent.host.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActPlayerDemoBinding;

public class PlayerDemoActivity extends ComponentActivity<ActPlayerDemoBinding> {
    @Override
    protected ActPlayerDemoBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActPlayerDemoBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regComponent(new PlayerComponent(getLifecycle()));
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, PlayerDemoActivity.class);
        context.startActivity(intent);
    }
}
