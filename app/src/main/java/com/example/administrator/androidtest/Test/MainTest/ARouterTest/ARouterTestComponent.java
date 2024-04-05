package com.example.administrator.androidtest.Test.MainTest.ARouterTest;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class ARouterTestComponent extends TestActivityComponent {

    public ARouterTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.ARouterTestButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ARouterTestButton:
                ARouter.getInstance().build("/ARouter/Module1Activity")
                        .withString("name", "老王")
                        .withInt("age", 18)
                        .navigation(getContext());
                break;

            default:
                break;
        }
    }
}
