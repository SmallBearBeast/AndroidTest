package com.example.administrator.androidtest.demo.widgetDemo.MarqueeDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.widgetDemo.BaseWidgetDemoComponent;
import com.example.administrator.androidtest.widget.MarqueeTextView;

public class MarqueeDemoComponent extends BaseWidgetDemoComponent {
    private MarqueeTextView marqueeTextView_1;

    public MarqueeDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        marqueeTextView_1 = getViewBinding().marqueeTextView1;
        getViewBinding().startMarqueeButton.setOnClickListener(this);
        getViewBinding().endMarqueeButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startMarqueeButton:
                marqueeTextView_1.beginMarquee(1000);
                break;
            case R.id.endMarqueeButton:
                marqueeTextView_1.endMarquee();
                break;
            default:
                break;
        }
    }
}
