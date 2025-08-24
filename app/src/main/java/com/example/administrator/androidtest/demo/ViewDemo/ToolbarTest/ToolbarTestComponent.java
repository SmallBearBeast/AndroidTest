package com.example.administrator.androidtest.demo.ViewDemo.ToolbarTest;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.ViewDemo.BaseViewDemoComponent;

public class ToolbarTestComponent extends BaseViewDemoComponent {
    public ToolbarTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        getViewBinding().toolbarTestButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbarTestButton) {
            ToolbarTestActivity.start(getContext());
        }
    }
}
