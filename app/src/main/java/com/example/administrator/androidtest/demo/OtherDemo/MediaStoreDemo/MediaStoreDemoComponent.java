package com.example.administrator.androidtest.demo.OtherDemo.MediaStoreDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ui.ActivityComponent;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActOtherDemoListBinding;

public class MediaStoreDemoComponent extends ActivityComponent<ActOtherDemoListBinding> implements View.OnClickListener {
    public MediaStoreDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        getViewBinding().mediaStoreDemoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.mediaStoreDemoButton) {
            MediaStoreDemoActivity.start(getContext());
        }
    }
}
