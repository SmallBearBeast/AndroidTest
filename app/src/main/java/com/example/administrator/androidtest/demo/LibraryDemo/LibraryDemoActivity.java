package com.example.administrator.androidtest.demo.LibraryDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.LibraryDemo.GlideDemo.GlideDemoComponent;
import com.example.administrator.androidtest.demo.LibraryDemo.OkHttpDemo.OkHttpDemoComponent;

public class LibraryDemoActivity extends ComponentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new GlideDemoComponent(getLifecycle()));
        regActComponent(new OkHttpDemoComponent(getLifecycle()));
    }

    @Override
    protected int layoutId() {
        return R.layout.act_library_demo_list;
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, LibraryDemoActivity.class);
        context.startActivity(intent);
    }
}
