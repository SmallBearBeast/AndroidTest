package com.example.administrator.androidtest.demo.ViewDemo.ToolbarTest;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class ToolbarTestComponent extends TestActivityComponent {
    public ToolbarTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.toolbarTestButton);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbarTestButton) {
            ToolbarTestActivity.start(getContext());
        }
    }
}
