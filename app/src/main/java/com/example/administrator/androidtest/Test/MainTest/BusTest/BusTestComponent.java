package com.example.administrator.androidtest.Test.MainTest.BusTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.BottomSheetTest.VpAndRvBottomSheetFragment;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class BusTestComponent extends TestActivityComponent {

    public BusTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.busTestButton);
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
