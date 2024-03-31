package com.example.administrator.androidtest.Test.MainTest.ViewDemo;

import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class ViewDemoComponent extends TestActivityComponent {

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.viewDemoButton);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.viewDemoButton) {
            ViewDemoAct.start(getContext());
        }
    }
}
