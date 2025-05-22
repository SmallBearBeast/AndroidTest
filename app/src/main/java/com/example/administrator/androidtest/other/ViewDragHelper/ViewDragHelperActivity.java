package com.example.administrator.androidtest.other.ViewDragHelper;

import android.view.LayoutInflater;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActViewDragHelperBinding;

public class ViewDragHelperActivity extends ComponentActivity<ActViewDragHelperBinding> {
    @Override
    protected ActViewDragHelperBinding inflateViewBinding(LayoutInflater inflater) {
        return ActViewDragHelperBinding.inflate(inflater);
    }
}
