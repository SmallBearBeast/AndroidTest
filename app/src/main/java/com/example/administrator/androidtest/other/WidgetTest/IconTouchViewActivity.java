package com.example.administrator.androidtest.other.WidgetTest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bear.libcommon.util.ToastUtil;
import com.bear.libcommon.util.ViewUtil;
import com.bear.libcommon.util.XmlDrawableUtil;
import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActIconTouchViewBinding;

public class IconTouchViewActivity extends ComponentActivity<ActIconTouchViewBinding> {
    private ImageView mIvTest_1;
    private FrameLayout mFlTest_1;
    private ImageView mIvTest_2;
    private FrameLayout mFlTest_2;
    private TextView mTvTest_3;
    private FrameLayout mFlTest_3;

    @Override
    protected ActIconTouchViewBinding inflateViewBinding(LayoutInflater inflater) {
        return ActIconTouchViewBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIvTest_1 = findViewById(R.id.iv_test_1);
        mFlTest_1 = findViewById(R.id.fl_test_1);
        mIvTest_2 = findViewById(R.id.iv_test_2);
        mFlTest_2 = findViewById(R.id.fl_test_2);
        mTvTest_3 = findViewById(R.id.tv_test_3);
        mFlTest_3 = findViewById(R.id.fl_test_3);
        XmlDrawableUtil.slAlphaRect(true, 1f, 0.5f, R.color.cl_transparent, 5).setView(mIvTest_1);
        ViewUtil.ivColorFilter(mIvTest_2, R.color.cl_black_t_4, R.color.cl_red_t_6);
        ViewUtil.tvTextColor(mTvTest_3, R.color.cl_black_t_4, R.color.cl_red_t_6);
        mIvTest_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("iv_test_2");
            }
        });
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
//        mFlTest_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.showToast("fl_test_2");
//            }
//        });
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
