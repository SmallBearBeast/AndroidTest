package com.example.administrator.androidtest.demo.ComponentDemo;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentFragment;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.ComponentDemo.Component.DemoFragComponent;

import java.util.ArrayList;

public class ComponentDemoFragment extends ComponentFragment {

    @Override
    protected int layoutId() {
        return R.layout.frag_component_test;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int position = getArguments().getInt(DemoFragComponent.KEY_POSITION);
            regFragComponent(new DemoFragComponent(getLifecycle()), position);
        }
    }

    public static ComponentDemoFragment get(int position, String mainText, String minorText, int mainBgColor, int minorBgColor, ArrayList<String> buttonTextList) {
        Bundle bundle = new Bundle();
        bundle.putInt(DemoFragComponent.KEY_POSITION, position);
        bundle.putString(DemoFragComponent.KEY_MAIN_TEXT, mainText);
        bundle.putString(DemoFragComponent.KEY_MINOR_TEXT, minorText);
        bundle.putInt(DemoFragComponent.KEY_MAIN_BG_COLOR, mainBgColor);
        bundle.putInt(DemoFragComponent.KEY_MINOR_BG_COLOR, minorBgColor);
        bundle.putStringArrayList(DemoFragComponent.KEY_BUTTON_TEXT_LIST, buttonTextList);
        ComponentDemoFragment componentDemoFrag = new ComponentDemoFragment();
        componentDemoFrag.setArguments(bundle);
        return componentDemoFrag;
    }
}
