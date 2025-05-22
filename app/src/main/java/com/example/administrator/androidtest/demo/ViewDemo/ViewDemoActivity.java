package com.example.administrator.androidtest.demo.ViewDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
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
        regActComponent(new RecyclerViewDemoComponent(getLifecycle()));
        regActComponent(new ViewPagerTestComponent(getLifecycle()));
        regActComponent(new ViewGetSizeTestComponent(getLifecycle()));
        regActComponent(new Viewpager2TestComponent(getLifecycle()));
        regActComponent(new MotionEventDemoComponent(getLifecycle()));
        regActComponent(new BehaviorTestComponent(getLifecycle()));
        regActComponent(new CoordinatorLayoutTestComponent(getLifecycle()));
        regActComponent(new EditTextTestComponent(getLifecycle()));
        regActComponent(new ToolbarTestComponent(getLifecycle()));
    }

    @Override
    protected ActViewDemoListBinding inflateViewBinding(LayoutInflater inflater) {
        return ActViewDemoListBinding.inflate(inflater);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ViewDemoActivity.class));
    }
}
