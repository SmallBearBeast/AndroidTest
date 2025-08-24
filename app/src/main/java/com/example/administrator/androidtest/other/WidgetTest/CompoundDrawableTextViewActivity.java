package com.example.administrator.androidtest.other.WidgetTest;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcommon.util.ToastUtil;
import com.bear.libcomponent.host.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActCompoundDrawableTvBinding;
import com.example.administrator.androidtest.widget.CompoundDrawableTextView;

public class CompoundDrawableTextViewActivity extends ComponentActivity<ActCompoundDrawableTvBinding> {
    private CompoundDrawableTextView cdtvText;

    @Override
    protected ActCompoundDrawableTvBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActCompoundDrawableTvBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cdtvText = findViewById(R.id.cdtv_text);
        cdtvText.setClickWrapper(new CompoundDrawableTextView.ClickWrapper() {

            @Override
            protected void onClick() {
                ToastUtil.showToast("onClick");
            }

        });
    }
}
