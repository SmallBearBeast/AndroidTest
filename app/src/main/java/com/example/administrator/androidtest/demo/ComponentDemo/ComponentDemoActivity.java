package com.example.administrator.androidtest.demo.ComponentDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActComponentTestBinding;
import com.example.administrator.androidtest.demo.ComponentDemo.Component.DemoActComponent;

public class ComponentDemoActivity extends ComponentActivity<ActComponentTestBinding> {
    @Override
    protected ActComponentTestBinding inflateViewBinding(LayoutInflater inflater) {
        return ActComponentTestBinding.inflate(inflater);
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
