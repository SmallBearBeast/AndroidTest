package com.example.administrator.androidtest.demo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bear.libcomponent.component.ComponentFragment;

public class TestFragment extends ComponentFragment {

    private static final String KEY_TEST_NAME = "KEY_TEST_NAME";
    private String testName = "";

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    protected View layoutView() {
        TextView textView = new TextView(requireContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setText(testName);
        return textView;
    }

    @Override
    protected void handleArgument(@NonNull Bundle bundle) {
        testName = bundle.getString(KEY_TEST_NAME);
    }

    public static TestFragment get(String testName) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TEST_NAME, testName);
        TestFragment testFragment = new TestFragment();
        testFragment.setArguments(bundle);
        return testFragment;
    }
}
