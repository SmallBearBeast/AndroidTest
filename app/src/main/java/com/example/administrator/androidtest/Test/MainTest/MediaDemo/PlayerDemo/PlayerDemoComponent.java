package com.example.administrator.androidtest.Test.MainTest.MediaDemo.PlayerDemo;

import android.annotation.SuppressLint;
import android.view.View;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;

public class PlayerDemoComponent extends ActivityComponent implements View.OnClickListener {
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
