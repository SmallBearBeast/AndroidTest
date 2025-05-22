package com.example.administrator.androidtest.demo.ComponentDemo;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.demo.ComponentDemo.Component.DemoFragComponent;
import com.example.administrator.androidtest.demo.ComponentDemo.Component.DemoMainTvComponent;
import com.example.administrator.androidtest.demo.ComponentDemo.Component.DemoMinorTvComponent;

import java.util.ArrayList;

public class ComponentSpecialDemoFragment extends ComponentDemoFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int position = getArguments().getInt(DemoFragComponent.KEY_POSITION);
            DemoFragComponent demoFragComponent = getComponent(DemoFragComponent.class, position);
            if (demoFragComponent != null) {
                demoFragComponent.regComponent(new DemoMainTvComponent(getLifecycle()));
                demoFragComponent.regComponent(new DemoMinorTvComponent(getLifecycle()));
            }
        }
    }


    public static ComponentSpecialDemoFragment get(int position, String mainText, String minorText, int mainBgColor, int minorBgColor, ArrayList<String> buttonTextList) {
        Bundle bundle = new Bundle();
        bundle.putInt(DemoFragComponent.KEY_POSITION, position);
        bundle.putString(DemoFragComponent.KEY_MAIN_TEXT, mainText);
        bundle.putString(DemoFragComponent.KEY_MINOR_TEXT, minorText);
        bundle.putInt(DemoFragComponent.KEY_MAIN_BG_COLOR, mainBgColor);
        bundle.putInt(DemoFragComponent.KEY_MINOR_BG_COLOR, minorBgColor);
        bundle.putStringArrayList(DemoFragComponent.KEY_BUTTON_TEXT_LIST, buttonTextList);
        ComponentSpecialDemoFragment componentSpecialDemoFrag = new ComponentSpecialDemoFragment();
        componentSpecialDemoFrag.setArguments(bundle);
        return componentSpecialDemoFrag;
    }
}
