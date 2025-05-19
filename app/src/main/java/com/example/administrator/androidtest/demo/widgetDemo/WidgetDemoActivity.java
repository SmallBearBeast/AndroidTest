package com.example.administrator.androidtest.demo.widgetDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.widgetDemo.BottomViewTest.BottomViewTestComponent;
import com.example.administrator.androidtest.demo.widgetDemo.CaseViewTest.CaseViewComponent;
import com.example.administrator.androidtest.demo.widgetDemo.FlowLayoutTest.FlowLayoutTestComponent;
import com.example.administrator.androidtest.demo.widgetDemo.FullTextViewDemo.FullTextViewDemoComponent;
import com.example.administrator.androidtest.demo.widgetDemo.LikeViewDemo.LikeViewDemoComponent;
import com.example.administrator.androidtest.demo.widgetDemo.LoopViewPagerDemo.LoopViewPagerDemoComponent;
import com.example.administrator.androidtest.demo.widgetDemo.MarqueeDemo.MarqueeDemoComponent;
import com.example.administrator.androidtest.demo.widgetDemo.pdfViewDemo.PdfViewDemoComponent;

public class WidgetDemoActivity extends ComponentActivity {

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
        context.startActivity(new Intent(context, WidgetDemoActivity.class));
    }
}
