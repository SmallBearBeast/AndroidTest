package com.example.administrator.androidtest.Test.MainTest.ComponentDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.ComponentDemo.Component.DemoActComponent;

public class ComponentDemoAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_component_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new DemoActComponent());
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ComponentDemoAct.class));
    }
}
