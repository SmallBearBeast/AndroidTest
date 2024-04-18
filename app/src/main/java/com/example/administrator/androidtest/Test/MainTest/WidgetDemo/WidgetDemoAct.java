package com.example.administrator.androidtest.Test.MainTest.WidgetDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.WidgetDemo.BottomViewTest.BottomViewTestComponent;
import com.example.administrator.androidtest.Test.MainTest.WidgetDemo.CaseViewTest.CaseViewComponent;
import com.example.administrator.androidtest.Test.MainTest.WidgetDemo.FlowLayoutTest.FlowLayoutTestComponent;
import com.example.administrator.androidtest.Test.MainTest.WidgetDemo.FullTextViewDemo.FullTextViewDemoComponent;
import com.example.administrator.androidtest.Test.MainTest.WidgetDemo.LikeViewDemo.LikeViewDemoComponent;
import com.example.administrator.androidtest.Test.MainTest.WidgetDemo.LoopViewPagerDemo.LoopViewPagerDemoComponent;
import com.example.administrator.androidtest.Test.MainTest.WidgetDemo.MarqueeDemo.MarqueeDemoComponent;
import com.example.administrator.androidtest.Test.MainTest.ViewDemo.ToolbarTest.ToolbarTestComponent;
import com.example.administrator.androidtest.Test.MainTest.WidgetDemo.PdfViewDemo.PdfViewDemoComponent;

public class WidgetDemoAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new PdfViewDemoComponent(getLifecycle()));
        regActComponent(new FlowLayoutTestComponent(getLifecycle()));
        regActComponent(new MarqueeDemoComponent(getLifecycle()));
        regActComponent(new CaseViewComponent(getLifecycle()));
        regActComponent(new BottomViewTestComponent(getLifecycle()));
        regActComponent(new LikeViewDemoComponent(getLifecycle()));
        regActComponent(new FullTextViewDemoComponent(getLifecycle()));
        regActComponent(new LoopViewPagerDemoComponent(getLifecycle()));
    }

    @Override
    protected int layoutId() {
        return R.layout.act_widget_demo_list;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, WidgetDemoAct.class));
    }
}
