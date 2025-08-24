package com.example.administrator.androidtest.other.XmlDrawableTest;


import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.bear.libcomponent.host.ComponentActivity;

// TODO: 2019/4/24 单独调用RotateDrawable.setPivox()是否出现死循环
public class DrawableTestActivity extends ComponentActivity {
    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return null;
    }
}
