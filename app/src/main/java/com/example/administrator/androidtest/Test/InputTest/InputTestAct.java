package com.example.administrator.androidtest.Test.InputTest;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.DensityUtil;

public class InputTestAct extends ComponentAct {

    private View clContent;
    private Button bt_1;
    private EditText et_1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clContent = findViewById(R.id.cl_content);
        clContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int computeUsableHeight = computeUsableHeight();
                int viewHeight = clContent.getHeight();
                int screenHeight = DensityUtil.getScreenHeight();
                Log.d(TAG, "onGlobalLayout: computeUsableHeight = " + computeUsableHeight + ", viewHeight = " + viewHeight + ", screenHeight = " + screenHeight);
            }
        });

        bt_1 = findViewById(R.id.bt_1);
        et_1 = findViewById(R.id.et_1);
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        clContent.getWindowVisibleDisplayFrame(r);
        // 全屏模式下：直接返回r.bottom，r.top其实是状态栏的高度
        return r.bottom;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_input_test;
    }
}
