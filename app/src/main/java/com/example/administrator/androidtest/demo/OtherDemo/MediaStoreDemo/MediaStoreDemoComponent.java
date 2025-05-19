package com.example.administrator.androidtest.demo.OtherDemo.MediaStoreDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class MediaStoreDemoComponent extends TestActivityComponent {
    public MediaStoreDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.mediaStoreDemoButton);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.mediaStoreDemoButton) {
            MediaStoreDemoActivity.start(getContext());
        }
    }
}
