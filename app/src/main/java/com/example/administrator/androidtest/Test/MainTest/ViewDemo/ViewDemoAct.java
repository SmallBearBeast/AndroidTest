package com.example.administrator.androidtest.Test.MainTest.ViewDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.FragTest.FragLifecycleTestAct;
import com.example.administrator.androidtest.Test.MainTest.ViewDemo.CoordinatorLayoutTest.BehaviorTestComponent;
import com.example.administrator.androidtest.Test.MainTest.ViewDemo.CoordinatorLayoutTest.CoordinatorLayoutTestComponent;
import com.example.administrator.androidtest.Test.MainTest.ViewDemo.EditTextTest.EditTextTestComponent;
import com.example.administrator.androidtest.Test.MainTest.ViewDemo.MotionTest.MotionEventDemoComponent;
import com.example.administrator.androidtest.Test.MainTest.ViewDemo.RecyclerViewTest.RecyclerViewTestComponent;
import com.example.administrator.androidtest.Test.MainTest.ViewDemo.ToolbarTest.ToolbarTestComponent;
import com.example.administrator.androidtest.Test.MainTest.ViewDemo.ViewGetSizeTest.ViewGetSizeTestComponent;
import com.example.administrator.androidtest.Test.MainTest.ViewDemo.ViewPager2Test.Viewpager2TestComponent;
import com.example.administrator.androidtest.Test.MainTest.ViewDemo.ViewPagerTest.ViewPagerTestComponent;

public class ViewDemoAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new RecyclerViewTestComponent(getLifecycle()));
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
    protected int layoutId() {
        return R.layout.act_view_demo_list;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ViewDemoAct.class));
    }
}
