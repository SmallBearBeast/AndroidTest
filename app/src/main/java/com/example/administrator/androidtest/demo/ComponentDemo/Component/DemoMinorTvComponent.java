package com.example.administrator.androidtest.demo.ComponentDemo.Component;

import android.widget.TextView;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.FragmentComponent;
import com.example.administrator.androidtest.databinding.FragComponentTestBinding;

public class DemoMinorTvComponent extends FragmentComponent<FragComponentTestBinding> {

    private TextView textMinorTv;

    private String originText;

    public DemoMinorTvComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
//        textMinorTv = findViewById(R.id.textMinorTv);
//        textMinorTv.setClickable(true);
//        textMinorTv.setOnClickListener(v -> getComponent(TestMainTvComponent.class).showMainTv());
    }

    @Override
    protected void onAttachViewBinding(FragComponentTestBinding binding) {
        super.onAttachViewBinding(binding);
        textMinorTv = getBinding().textMinorTv;
        originText = String.valueOf(textMinorTv.getText());
        textMinorTv.setClickable(true);
        textMinorTv.setOnClickListener(v -> getComponent(DemoMainTvComponent.class).resetMainTv());
    }

    public void showMinorTv() {
        textMinorTv.setText("I am minorTv");
    }

    public void resetMinorTv() {
        textMinorTv.setText(originText);
    }
}
