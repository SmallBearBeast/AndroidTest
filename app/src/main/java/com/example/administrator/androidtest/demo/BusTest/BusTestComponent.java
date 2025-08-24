package com.example.administrator.androidtest.demo.BusTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.BottomSheetTest.VpAndRvBottomSheetFragment;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class BusTestComponent extends TestActivityComponent {

    public BusTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        getViewBinding().busTestButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.busTestButton:
                VpAndRvBottomSheetFragment.show(getActivity().getSupportFragmentManager());
//                BusTest1Act.start(getContext());
                break;
        }
    }
}
