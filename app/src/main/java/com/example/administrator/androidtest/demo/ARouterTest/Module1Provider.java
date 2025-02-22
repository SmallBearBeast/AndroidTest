package com.example.administrator.androidtest.demo.ARouterTest;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/ARouter/Module1Provider", name = "Module1Provider")
public class Module1Provider implements IModule1Provider{
    @Override
    public String getName() {
        return "Hello World";
    }

    @Override
    public void init(Context context) {

    }
}
