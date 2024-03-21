package com.example.administrator.androidtest.Test.MainTest.OkHttpTest;

import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class OkHttpTestComponent extends TestComponent {

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.okhttpTestButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.okhttpTestButton:
                OkHttpAct.start(getContext());
                break;
        }
    }
}
