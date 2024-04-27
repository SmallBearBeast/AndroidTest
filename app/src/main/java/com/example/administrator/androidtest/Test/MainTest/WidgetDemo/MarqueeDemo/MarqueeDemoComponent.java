package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.MarqueeDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;
import com.example.administrator.androidtest.Widget.MarqueeTextView;

public class MarqueeDemoComponent extends TestActivityComponent {
    private MarqueeTextView marqueeTextView_1;

    public MarqueeDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        marqueeTextView_1 = findViewById(R.id.marqueeTextView_1);
        setOnClickListener(this, R.id.startMarqueeButton, R.id.endMarqueeButton);
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
