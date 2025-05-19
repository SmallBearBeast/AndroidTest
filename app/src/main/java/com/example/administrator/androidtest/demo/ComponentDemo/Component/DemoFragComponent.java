package com.example.administrator.androidtest.demo.ComponentDemo.Component;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.FragmentComponent;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.widget.FlowLayout;
import com.example.libcommon.util.StringUtil;
import com.example.libcommon.util.ToastUtil;

import java.util.List;

public class DemoFragComponent extends FragmentComponent implements View.OnClickListener {

    public static final String KEY_POSITION = "KEY_POSITION";
    public static final String KEY_MAIN_TEXT = "KEY_MAIN_TEXT";
    public static final String KEY_MINOR_TEXT = "KEY_MINOR_TEXT";
    public static final String KEY_MAIN_BG_COLOR = "KEY_MAIN_BG_COLOR";
    public static final String KEY_MINOR_BG_COLOR = "KEY_MINOR_BG_COLOR";
    public static final String KEY_BUTTON_TEXT_LIST = "KEY_BUTTON_TEXT_LIST";

    private int position = -1;

    public DemoFragComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreateView() {
        super.onCreateView();
        FlowLayout flowLayout = findViewById(R.id.flowLayout);
        TextView textMainTv = findViewById(R.id.textMainTv);
        TextView textMinorTv = findViewById(R.id.textMinorTv);
        if (getArguments() != null) {
            position = getArguments().getInt(KEY_POSITION);
            String mainText = getArguments().getString(KEY_MAIN_TEXT);
            String minorText = getArguments().getString(KEY_MINOR_TEXT);
            int mainColor = getArguments().getInt(KEY_MAIN_BG_COLOR, Color.WHITE);
            int minorColor = getArguments().getInt(KEY_MINOR_BG_COLOR, Color.WHITE);
            textMainTv.setVisibility(StringUtil.isEmpty(mainText) ? View.GONE : View.VISIBLE);
            textMinorTv.setVisibility(StringUtil.isEmpty(minorText) ? View.GONE : View.VISIBLE);
            if (!StringUtil.isEmpty(mainText)) {
                textMainTv.setText(mainText);
                textMainTv.setBackgroundColor(mainColor);
            }
            if (!StringUtil.isEmpty(minorText)) {
                textMinorTv.setText(minorText);
                textMinorTv.setBackgroundColor(minorColor);
            }

            List<String> buttonTextList = getArguments().getStringArrayList(KEY_BUTTON_TEXT_LIST);
            for (String text : buttonTextList) {
                Button button = new Button(getContext());
                button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                button.setAllCaps(false);
                button.setText(text);
                button.setTag(text);
                button.setOnClickListener(this);
                flowLayout.addView(button);
            }
        }
    }

    public void showToast() {
        ToastUtil.showToast("I am " + TAG + position);
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        if (tag == null) {
            return;
        }
        if (tag.startsWith("Call DemoFragComponent")) {
            String numStr = tag.substring(tag.lastIndexOf(' ') + 1);
            int num = Integer.valueOf(numStr);
            DemoFragComponent component = getComponent(DemoFragComponent.class, num);
            if (component != null) {
                component.showToast();
            }
        }
        switch (tag) {
            case "Call DemoActComponent":
                DemoActComponent component = getComponent(DemoActComponent.class);
                if (component != null) {
                    component.showToast();
                }
                break;

            case "Call ShowMainTv":
                DemoMainTvComponent mainComponent = getComponent(DemoMainTvComponent.class);
                if (mainComponent != null) {
                    mainComponent.showMainTv();
                }
                break;

            case "Call ShowMinorTv":
                DemoMinorTvComponent minorComponent = getComponent(DemoMinorTvComponent.class);
                if (minorComponent != null) {
                    minorComponent.showMinorTv();
                }
                break;
        }
    }
}
