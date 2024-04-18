package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.PdfViewDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class PdfViewDemoComponent extends TestActivityComponent {
    public PdfViewDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.pdfViewButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pdfViewButton:
                PdfViewDemoAct.go(getContext());
                break;
        }
    }
}
