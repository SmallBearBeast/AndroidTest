package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.BottomViewTest;

import android.app.Activity;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.BottomView;

public class NsBottomView extends BottomView {
    public NsBottomView(Activity activity) {
        super(activity);
        contentView(R.layout.view_ns_bottom_test);
    }
}
