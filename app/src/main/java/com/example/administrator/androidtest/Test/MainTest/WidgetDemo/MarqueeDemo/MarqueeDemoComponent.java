package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.MarqueeDemo;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;
import com.example.administrator.androidtest.Widget.MarqueeTextView;

public class MarqueeDemoComponent extends TestComponent {
    private MarqueeTextView marqueeTextView_1;

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
                marqueeTextView_1.startMarquee(1000);
                break;
            case R.id.endMarqueeButton:
                marqueeTextView_1.endMarquee();
                break;
            default:
                break;
        }
    }
}
