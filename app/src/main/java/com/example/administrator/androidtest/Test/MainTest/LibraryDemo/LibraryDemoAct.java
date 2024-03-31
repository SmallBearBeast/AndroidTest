package com.example.administrator.androidtest.Test.MainTest.LibraryDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.LibraryDemo.GlideDemo.GlideDemoComponent;
import com.example.administrator.androidtest.Test.MainTest.LibraryDemo.OkHttpDemo.OkHttpDemoComponent;

public class LibraryDemoAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new GlideDemoComponent());
        regActComponent(new OkHttpDemoComponent());
    }

    @Override
    protected int layoutId() {
        return R.layout.act_library_demo_list;
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, LibraryDemoAct.class);
        context.startActivity(intent);
    }
}
