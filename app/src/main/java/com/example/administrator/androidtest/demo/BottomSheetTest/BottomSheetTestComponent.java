package com.example.administrator.androidtest.demo.BottomSheetTest;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class BottomSheetTestComponent extends TestActivityComponent {

    public BottomSheetTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        getViewBinding().bottomSheetTestButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottomSheetTestButton:
                VpAndRvBottomSheetFragment.show(getActivity().getSupportFragmentManager());
                break;

            default:break;
        }
    }
}
