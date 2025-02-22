package com.example.administrator.androidtest.demo.WidgetDemo.BottomViewTest;

import android.app.Activity;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.widget.BottomView;

public class NsBottomView extends BottomView {
    public NsBottomView(Activity activity) {
        super(activity);
        contentView(R.layout.view_ns_bottom_test);
    }
}
