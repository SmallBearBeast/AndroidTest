package com.example.administrator.androidtest.demo.ViewDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcomponent.host.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActViewDemoListBinding;
import com.example.administrator.androidtest.demo.ViewDemo.CoordinatorLayoutTest.BehaviorTestComponent;
import com.example.administrator.androidtest.demo.ViewDemo.CoordinatorLayoutTest.CoordinatorLayoutTestComponent;
import com.example.administrator.androidtest.demo.ViewDemo.EditTextTest.EditTextTestComponent;
import com.example.administrator.androidtest.demo.ViewDemo.MotionTest.MotionEventDemoComponent;
import com.example.administrator.androidtest.demo.ViewDemo.RecyclerViewDemo.RecyclerViewDemoComponent;
import com.example.administrator.androidtest.demo.ViewDemo.ToolbarTest.ToolbarTestComponent;
import com.example.administrator.androidtest.demo.ViewDemo.ViewGetSizeTest.ViewGetSizeTestComponent;
import com.example.administrator.androidtest.demo.ViewDemo.ViewPager2Test.Viewpager2TestComponent;
import com.example.administrator.androidtest.demo.ViewDemo.ViewPagerTest.ViewPagerTestComponent;

public class ViewDemoActivity extends ComponentActivity<ActViewDemoListBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regComponent(new RecyclerViewDemoComponent(getLifecycle()));
        regComponent(new ViewPagerTestComponent(getLifecycle()));
        regComponent(new ViewGetSizeTestComponent(getLifecycle()));
        regComponent(new Viewpager2TestComponent(getLifecycle()));
        regComponent(new MotionEventDemoComponent(getLifecycle()));
        regComponent(new BehaviorTestComponent(getLifecycle()));
        regComponent(new CoordinatorLayoutTestComponent(getLifecycle()));
        regComponent(new EditTextTestComponent(getLifecycle()));
        regComponent(new ToolbarTestComponent(getLifecycle()));
    }

    @Override
    protected ActViewDemoListBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActViewDemoListBinding.inflate(inflater);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ViewDemoActivity.class));
    }
}
