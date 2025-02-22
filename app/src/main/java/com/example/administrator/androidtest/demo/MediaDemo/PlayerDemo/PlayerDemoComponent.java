package com.example.administrator.androidtest.demo.MediaDemo.PlayerDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class PlayerDemoComponent extends TestActivityComponent {
    public PlayerDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.playerDemoButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playerDemoButton:
                PlayerDemoAct.go(getContext());
                break;

            default:
                break;
        }
    }
}
