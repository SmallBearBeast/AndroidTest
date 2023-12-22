package com.example.administrator.androidtest.Test.MainTest.ComponentDemo;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentFrag;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.ComponentDemo.Component.DemoFragComponent;

import java.util.ArrayList;

public class ComponentDemoFrag extends ComponentFrag {

    @Override
    protected int layoutId() {
        return R.layout.frag_component_test;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int position = getArguments().getInt(DemoFragComponent.KEY_POSITION);
            regFragComponent(new DemoFragComponent(), position);
        }
    }

    public static ComponentDemoFrag get(int position, String mainText, String minorText, int mainBgColor, int minorBgColor, ArrayList<String> buttonTextList) {
        Bundle bundle = new Bundle();
        bundle.putInt(DemoFragComponent.KEY_POSITION, position);
        bundle.putString(DemoFragComponent.KEY_MAIN_TEXT, mainText);
        bundle.putString(DemoFragComponent.KEY_MINOR_TEXT, minorText);
        bundle.putInt(DemoFragComponent.KEY_MAIN_BG_COLOR, mainBgColor);
        bundle.putInt(DemoFragComponent.KEY_MINOR_BG_COLOR, minorBgColor);
        bundle.putStringArrayList(DemoFragComponent.KEY_BUTTON_TEXT_LIST, buttonTextList);
        ComponentDemoFrag componentDemoFrag = new ComponentDemoFrag();
        componentDemoFrag.setArguments(bundle);
        return componentDemoFrag;
    }
}
