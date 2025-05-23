package com.example.administrator.androidtest.demo.widgetDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActWidgetDemoListBinding;
import com.example.administrator.androidtest.demo.widgetDemo.BottomViewTest.BottomViewTestComponent;
import com.example.administrator.androidtest.demo.widgetDemo.CaseViewTest.CaseViewComponent;
import com.example.administrator.androidtest.demo.widgetDemo.FlowLayoutTest.FlowLayoutTestComponent;
import com.example.administrator.androidtest.demo.widgetDemo.FullTextViewDemo.FullTextViewDemoComponent;
import com.example.administrator.androidtest.demo.widgetDemo.LikeViewDemo.LikeViewDemoComponent;
import com.example.administrator.androidtest.demo.widgetDemo.LoopViewPagerDemo.LoopViewPagerDemoComponent;
import com.example.administrator.androidtest.demo.widgetDemo.MarqueeDemo.MarqueeDemoComponent;
import com.example.administrator.androidtest.demo.widgetDemo.pdfViewDemo.PdfViewDemoComponent;

public class WidgetDemoActivity extends ComponentActivity<ActWidgetDemoListBinding> {

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
    protected ActWidgetDemoListBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActWidgetDemoListBinding.inflate(inflater);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, WidgetDemoActivity.class));
    }
}
