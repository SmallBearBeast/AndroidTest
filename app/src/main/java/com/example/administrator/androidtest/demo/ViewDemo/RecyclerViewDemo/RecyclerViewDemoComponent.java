package com.example.administrator.androidtest.demo.ViewDemo.RecyclerViewDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.ViewDemo.BaseViewDemoComponent;

public class RecyclerViewDemoComponent extends BaseViewDemoComponent {
    public RecyclerViewDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        getViewBinding().recyclerViewDemoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.recyclerViewDemoButton) {
            RecyclerViewDemoActivity.start(getContext());
        }
    }
}
