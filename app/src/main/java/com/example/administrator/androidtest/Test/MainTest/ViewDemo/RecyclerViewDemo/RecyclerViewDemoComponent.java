package com.example.administrator.androidtest.Test.MainTest.ViewDemo.RecyclerViewDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;
public class RecyclerViewDemoComponent extends TestActivityComponent {
    public RecyclerViewDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.recyclerViewDemoButton);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.recyclerViewDemoButton) {
            RecyclerViewDemoAct.start(getContext());
        }
    }
}
