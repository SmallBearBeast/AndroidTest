package com.example.administrator.androidtest.Test.WidgetTest;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.ToastUtil;
import com.example.libbase.Util.XmlDrawableUtil;
import com.example.libframework.ActAndFrag.ComponentAct;

public class IconTouchViewAct extends ComponentAct {
    private ImageView mIvTest_1;
    private FrameLayout mFlTest_1;
    @Override
    protected int layoutId() {
        return R.layout.act_icon_touch_view;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mIvTest_1 = findViewById(R.id.iv_test_1);
        mFlTest_1 = findViewById(R.id.fl_test_1);
        XmlDrawableUtil.slAlphaCRect(1f, 0.5f, R.color.cl_green_9, 5).setView(mIvTest_1);
//        mFlTest_1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        mFlTest_1.setAlpha(0.5f);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        mFlTest_1.setAlpha(1f);
//                        mFlTest_1.performClick();
//                        break;
//                }
//                return true;
//            }
//        });

        mFlTest_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("fl_test_1");
            }
        });
    }

//    public void onClick(View view){
//        switch (view.getId()){
//            case R.id.bt_1:
//                break;
//
//            case R.id.iv_test_1:
//                ToastUtil.showToast("iv_test_1");
//                break;
//
//            case R.id.fl_test_1:
//                ToastUtil.showToast("fl_test_1");
//                break;
//        }
//    }
}
