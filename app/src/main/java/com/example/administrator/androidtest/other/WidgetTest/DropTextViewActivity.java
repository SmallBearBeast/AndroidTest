package com.example.administrator.androidtest.other.WidgetTest;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActDropTextviewBinding;

public class DropTextViewActivity extends ComponentActivity<ActDropTextviewBinding> {
    @Override
    protected ActDropTextviewBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActDropTextviewBinding.inflate(inflater);
    }
}
