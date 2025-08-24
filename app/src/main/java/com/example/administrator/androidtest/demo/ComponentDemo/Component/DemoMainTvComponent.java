package com.example.administrator.androidtest.demo.ComponentDemo.Component;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ui.FragmentComponent;
import com.example.administrator.androidtest.databinding.FragComponentTestBinding;

public class DemoMainTvComponent extends FragmentComponent<FragComponentTestBinding> {

    private TextView textMainTv;

    private String originText;

    public DemoMainTvComponent(Lifecycle lifecycle)  {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        // 这个时候View可能还没有初始化，下面代码可能会抛出空指针异常
//        textMainTv = findViewById(R.id.textMainTv);
//        textMainTv.setClickable(true);
//        textMainTv.setOnClickListener(v -> getComponent(TestMinorTvComponent.class).showMinorTv());
    }

    @Override
    public void attachViewBinding(@NonNull FragComponentTestBinding binding) {
        super.attachViewBinding(binding);
        textMainTv = getViewBinding().textMainTv;
        originText = String.valueOf(textMainTv.getText());
        textMainTv.setClickable(true);
        textMainTv.setOnClickListener(v -> getComponent(DemoMinorTvComponent.class).resetMinorTv());
    }

    public void showMainTv() {
        textMainTv.setText("I am mainTv");
    }

    public void resetMainTv() {
        textMainTv.setText(originText);
    }
}
