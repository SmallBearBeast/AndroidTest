package com.example.administrator.androidtest.Test.MainTest.BottomSheetTest;

import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class BottomSheetTestComponent extends TestComponent {

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.bottomSheetTestButton);
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
