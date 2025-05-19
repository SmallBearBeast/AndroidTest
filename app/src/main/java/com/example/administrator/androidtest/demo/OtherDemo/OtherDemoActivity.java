package com.example.administrator.androidtest.demo.OtherDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.OtherDemo.MediaStoreDemo.MediaStoreDemoComponent;

public class OtherDemoActivity extends ComponentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new MediaStoreDemoComponent(getLifecycle()));
    }

    @Override
    protected int layoutId() {
        return R.layout.act_other_demo_list;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, OtherDemoActivity.class));
    }
}
