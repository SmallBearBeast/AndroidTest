package com.example.administrator.androidtest.demo.widgetDemo.pdfViewDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.widgetDemo.BaseWidgetDemoComponent;

public class PdfViewDemoComponent extends BaseWidgetDemoComponent {
    public PdfViewDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        getViewBinding().pdfViewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pdfViewButton:
                PdfViewDemoActivity.go(getContext());
                break;
        }
    }
}
