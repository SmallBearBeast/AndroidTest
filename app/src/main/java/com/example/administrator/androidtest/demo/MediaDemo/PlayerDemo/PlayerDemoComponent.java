package com.example.administrator.androidtest.demo.MediaDemo.PlayerDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActMediaDemoListBinding;

public class PlayerDemoComponent extends ActivityComponent<ActMediaDemoListBinding> implements View.OnClickListener {
    public PlayerDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        getBinding().playerDemoButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playerDemoButton:
                PlayerDemoActivity.go(getContext());
                break;

            default:
                break;
        }
    }
}
