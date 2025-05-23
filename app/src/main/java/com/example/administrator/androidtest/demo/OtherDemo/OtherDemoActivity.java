package com.example.administrator.androidtest.demo.OtherDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActOtherDemoListBinding;
import com.example.administrator.androidtest.demo.OtherDemo.MediaStoreDemo.MediaStoreDemoComponent;

public class OtherDemoActivity extends ComponentActivity<ActOtherDemoListBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new MediaStoreDemoComponent(getLifecycle()));
    }

    @Override
    protected ActOtherDemoListBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActOtherDemoListBinding.inflate(inflater);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, OtherDemoActivity.class));
    }
}
