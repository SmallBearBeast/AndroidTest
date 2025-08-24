package com.example.administrator.androidtest.demo.ComponentDemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.bear.libcomponent.host.ComponentFragment;
import com.example.administrator.androidtest.databinding.FragComponentTestBinding;
import com.example.administrator.androidtest.demo.ComponentDemo.Component.DemoFragComponent;

import java.util.ArrayList;

public class ComponentDemoFragment extends ComponentFragment<FragComponentTestBinding> {
    @Override
    protected FragComponentTestBinding inflateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragComponentTestBinding.inflate(inflater, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int position = getArguments().getInt(DemoFragComponent.KEY_POSITION);
            regComponent(new DemoFragComponent(getLifecycle()), position);
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
