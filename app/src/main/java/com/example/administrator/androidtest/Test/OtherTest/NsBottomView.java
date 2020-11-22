package com.example.administrator.androidtest.Test.OtherTest;

import android.app.Activity;

import com.example.administrator.androidtest.R;
import com.example.libbase.Com.BottomView;

public class NsBottomView extends BottomView {
    protected NsBottomView(Activity activity) {
        super(activity);
        contentView(R.layout.view_ns_bottom_test);
    }
}
