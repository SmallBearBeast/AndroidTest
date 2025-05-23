package com.example.administrator.androidtest.other.ViewDragHelper;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActViewDragHelperBinding;

public class ViewDragHelperActivity extends ComponentActivity<ActViewDragHelperBinding> {
    @Override
    protected ActViewDragHelperBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActViewDragHelperBinding.inflate(inflater);
    }
}
