package com.example.administrator.androidtest.Test.MainTest.DialogTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class DialogTestComponent extends TestActivityComponent {
    public DialogTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.showCustomizeDialogButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.showCustomizeDialogButton:
                new TestDialog(getActivity()).show();
                break;

            default:
                break;
        }
    }
}
