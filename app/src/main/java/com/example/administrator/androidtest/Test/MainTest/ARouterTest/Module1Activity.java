package com.example.administrator.androidtest.Test.MainTest.ARouterTest;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;

@Route(path = "/ARouter/Module1Activity", name = "Module1Activity")
public class Module1Activity extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_temp;
    }
}
