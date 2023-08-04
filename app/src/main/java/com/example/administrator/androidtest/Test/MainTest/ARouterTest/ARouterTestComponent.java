package com.example.administrator.androidtest.Test.MainTest.ARouterTest;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class ARouterTestComponent extends TestComponent {

    @Override
    protected void onCreate() {
        super.onCreate();
        clickListener(this, R.id.ARouterTestButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ARouterTestButton:
                ARouter.getInstance().build("/ARouter/Module1Activity")
                        .withString("name", "老王")
                        .withInt("age", 18)
                        .navigation(getComActivity());
                break;

            default:
                break;
        }
    }
}
