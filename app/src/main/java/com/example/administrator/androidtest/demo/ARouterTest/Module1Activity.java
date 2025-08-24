package com.example.administrator.androidtest.demo.ARouterTest;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bear.libcomponent.host.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActTempBinding;

@Route(path = "/ARouter/Module1Activity", name = "Module1Activity")
public class Module1Activity extends ComponentActivity<ActTempBinding> {

    @Override
    protected ActTempBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActTempBinding.inflate(inflater);
    }
}
