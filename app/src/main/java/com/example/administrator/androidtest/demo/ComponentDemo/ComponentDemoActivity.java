package com.example.administrator.androidtest.demo.ComponentDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.ComponentDemo.Component.DemoActComponent;

public class ComponentDemoActivity extends ComponentActivity {
    @Override
    protected int layoutId() {
        return R.layout.act_component_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new DemoActComponent(getLifecycle()));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ComponentDemoActivity.class));
    }
}
