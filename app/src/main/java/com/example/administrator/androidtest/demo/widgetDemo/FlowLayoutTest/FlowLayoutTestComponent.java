package com.example.administrator.androidtest.demo.widgetDemo.FlowLayoutTest;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;

import com.bear.libcommon.util.DensityUtil;
import com.bear.libcommon.util.ToastUtil;
import com.bear.libcommon.util.XmlDrawableUtil;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.widgetDemo.BaseWidgetDemoComponent;
import com.example.administrator.androidtest.widget.FlowFakeTextView;
import com.example.administrator.androidtest.widget.FlowLayout;

public class FlowLayoutTestComponent extends BaseWidgetDemoComponent {
    public FlowLayoutTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        initFlowLayout();
        initFlowFakeFlowLayout();
    }

    private void initFlowLayout() {
        FlowLayout flowLayout = getViewBinding().flowLayout;
        String[] texts = new String[]{
                "Android, Java, PHP, C++, Android, Java, PHP, C++, Android, Java, PHP, C++",
                "Android, Java, PHP, C++, Android, Java, PHP, C++, Android, Java, PHP, C++",
                "Android", "Java", "PHP", "C++",
                "Android", "Java", "PHP", "C++", "Android", "Java", "PHP", "C++", "Android", "Java", "PHP", "C++",
        };
        for (String text : texts) {
            flowLayout.addView(createTv(text));
        }
        flowLayout.setFlowClickListener(view -> {
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                ToastUtil.showToast(String.valueOf(tv.getText()));
            }
        });
    }

    private void initFlowFakeFlowLayout() {
        FlowLayout flowFakeFlowLayout = getViewBinding().flowFakeFlowLayout;
        FlowFakeTextView flowFakeTextView = getViewBinding().flowFakeTextView;
        flowFakeTextView.setTvInitCallback(() -> createTv(""));
        flowFakeFlowLayout.post(() -> {
            flowFakeTextView.setText("我是一个好人，但是我喜欢干坏事。这真是个悲伤的故事。这真是个悲伤的故事。这真是个悲伤的故事", flowFakeFlowLayout.getWidth());
        });
    }

    private TextView createTv(String text) {
        TextView tv = new TextView(getContext());
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.topMargin = DensityUtil.dp2Px(5);
//        lp.bottomMargin = DensityUtil.dp2Px(5);
//        lp.setMarginStart(DensityUtil.dp2Px(5));
//        lp.setMarginEnd(DensityUtil.dp2Px(5));
        tv.setLayoutParams(lp);
        tv.setText(text);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        int padding = DensityUtil.dp2Px(5);
        tv.setPadding(padding, padding, padding, padding);
        XmlDrawableUtil.rect(true, R.color.cl_blue_5, 3).setView(tv);
        return tv;
    }

}
