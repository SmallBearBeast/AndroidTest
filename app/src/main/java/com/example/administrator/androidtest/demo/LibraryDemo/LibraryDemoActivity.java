package com.example.administrator.androidtest.demo.LibraryDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActLibraryDemoListBinding;
import com.example.administrator.androidtest.demo.LibraryDemo.GlideDemo.GlideDemoComponent;
import com.example.administrator.androidtest.demo.LibraryDemo.OkHttpDemo.OkHttpDemoComponent;

public class LibraryDemoActivity extends ComponentActivity<ActLibraryDemoListBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new GlideDemoComponent(getLifecycle()));
        regActComponent(new OkHttpDemoComponent(getLifecycle()));
    }

    @Override
    protected ActLibraryDemoListBinding inflateViewBinding(LayoutInflater inflater) {
        return ActLibraryDemoListBinding.inflate(inflater);
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, LibraryDemoActivity.class);
        context.startActivity(intent);
    }
}
