package com.example.administrator.androidtest.Test.FlowLayoutTest;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.FlowFakeTextView;
import com.example.administrator.androidtest.Widget.FlowLayout;
import com.example.libbase.Util.DensityUtil;
import com.example.libbase.Util.ToastUtil;
import com.example.libbase.Util.XmlDrawableUtil;

import java.util.ArrayList;
import java.util.List;

public class FlowLayoutTestAct extends ComponentAct {
    private FlowFakeTextView flowFakeTextView;
    private List<Integer> splitPointList = new ArrayList<>();
    @Override
    protected int layoutId() {
        return R.layout.act_flow_layout_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FlowLayout flowLayout = findViewById(R.id.fl_container);
        flowLayout.setFlowClickListener(new FlowLayout.OnFlowClickListener() {
            @Override
            public void onClick(View view) {
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    ToastUtil.showToast(String.valueOf(tv.getText()));
                }
            }
        });
//        flowFakeTextView = findViewById(R.id.fstv_content);
//        flowFakeTextView.setTvInitCallback(new FlowFakeTextView.TvInitCallback() {
//            @Override
//            public TextView onGetInitTv() {
//                return createTv("");
//            }
//        });
//        flowLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                flowFakeTextView.setText("我是一个好人，但是我喜欢干坏事。这真是个悲伤的故事。这真是个悲伤的故事。这真是个悲伤的故事。这真是个悲伤的故事。这真是个悲伤的故事。", flowLayout.getWidth());
//                flowLayout.addView(createTv("Hello World"));
//                splitPointList = new ArrayList<>(flowFakeTextView.getSplitPointList());
//            }
//        });
//        String[] texts = new String[]{
//                "Android", "Java", "PHP", "C++"
//                "Android", "Java", "PHP", "C++", "Android", "Java", "PHP", "C++", "Android", "Java", "PHP", "C++",
//                "Android", "Java", "PHP", "C++", "Android", "Java", "PHP", "C++", "Android", "Java", "PHP", "C++"
//        };
//        for (int i = 0; i < texts.length; i++) {
//            flowLayout.addView(createTv(texts[i]));
//        }
//        flowLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                View lastView = flowLayout.getChildAt(flowLayout.getChildCount() - 1);
//                int totalWidth = DensityUtil.getScreenWidth();
//                int startWidth = totalWidth - lastView.getRight() - flowLayout.getPaddingRight() - DensityUtil.dp2Px(5);
//                int contentWidth = totalWidth - flowLayout.getPaddingLeft() - flowLayout.getPaddingRight();
//                List<TextView> textViewList = splitTextView("我是一个好人，但是我喜欢干坏事。这真是个悲伤的故事。这真是个悲伤的故事。这真是个悲伤的故事。这真是个悲伤的故事。这真是个悲伤的故事。", startWidth, contentWidth);
//                for (TextView textView : textViewList) {
//                    flowLayout.addView(textView);
//                }
//                flowLayout.addView(createTv("Hello World"));
//            }
//        });
    }

    private TextView createTv(String text) {
        TextView tv = new TextView(this);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText(text);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        int padding = DensityUtil.dp2Px(5);
        tv.setPadding(padding, padding, padding, padding);
        XmlDrawableUtil.rect(true, R.color.cl_blue_5, 3).setView(tv);
        return tv;
    }


    private List<TextView> splitTextView(CharSequence charSequence, int startWidth, int contentWidth) {
        List<TextView> splitTvList = new ArrayList<>();
        TextView startTv = createTv("");
        Layout startLayout = obtainStaticLayout(startTv, charSequence, startWidth);
        int startPos = startLayout.getLineEnd(0);
        startTv.setText(charSequence.subSequence(0, startPos));
        splitTvList.add(startTv);

        if (startPos < charSequence.length()) {
            CharSequence contentCs = charSequence.subSequence(startPos, charSequence.length());
            TextView contentTv = createTv("");
            Layout contentLayout = obtainStaticLayout(contentTv, contentCs, contentWidth);
            int lineCount = contentLayout.getLineCount();
            int lineStart = 0;
            int lineEnd = 0;
            for (int i = 0; i < lineCount; i++) {
                TextView tv = createTv("");
                lineEnd = contentLayout.getLineEnd(i);
                tv.setText(contentCs.subSequence(lineStart, lineEnd));
                lineStart = lineEnd;
                splitTvList.add(tv);
            }
        }
        return splitTvList;
    }

    private Layout obtainStaticLayout(TextView tv, CharSequence charSequence, int width) {
        int contentWidth = width - tv.getPaddingLeft() - tv.getPaddingRight();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder builder = StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), tv.getPaint(), contentWidth);
            builder.setAlignment(Layout.Alignment.ALIGN_NORMAL);
            builder.setIncludePad(tv.getIncludeFontPadding());
            builder.setLineSpacing(tv.getLineSpacingExtra(), tv.getLineSpacingMultiplier());
            return builder.build();
        } else {
            return new StaticLayout(charSequence, tv.getPaint(), contentWidth, Layout.Alignment.ALIGN_NORMAL,
                    tv.getLineSpacingMultiplier(), tv.getLineSpacingExtra(), tv.getIncludeFontPadding());
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                flowFakeTextView.setText("我是一个好人，但是我喜欢干坏事。这真是个悲伤的故事。这真是个悲伤的故事。这真是个悲伤的故事。这真是个悲伤的故事。这真是个悲伤的故事。", splitPointList);
                break;

            case R.id.bt_2:
                flowFakeTextView.setText("", 0);
                break;
        }
    }
}
