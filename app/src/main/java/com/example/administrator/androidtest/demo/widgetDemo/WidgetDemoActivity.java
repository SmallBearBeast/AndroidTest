package com.example.administrator.androidtest.demo.widgetDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcomponent.host.ComponentActivity;
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
        regComponent(new PdfViewDemoComponent(getLifecycle()));
        regComponent(new FlowLayoutTestComponent(getLifecycle()));
        regComponent(new MarqueeDemoComponent(getLifecycle()));
        regComponent(new CaseViewComponent(getLifecycle()));
        regComponent(new BottomViewTestComponent(getLifecycle()));
        regComponent(new LikeViewDemoComponent(getLifecycle()));
        regComponent(new FullTextViewDemoComponent(getLifecycle()));
        regComponent(new LoopViewPagerDemoComponent(getLifecycle()));
        requireBinding().statefulImgTextView.setOnClickListener(v -> requireBinding().statefulImgTextView.setSelected(!requireBinding().statefulImgTextView.isSelected()));
        requireBinding().stateful2TextView.setOnClickListener(v -> requireBinding().stateful2TextView.setSelected(!requireBinding().stateful2TextView.isSelected()));
    }

    @Override
    protected ActWidgetDemoListBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActWidgetDemoListBinding.inflate(inflater);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, WidgetDemoActivity.class));
    }
}
