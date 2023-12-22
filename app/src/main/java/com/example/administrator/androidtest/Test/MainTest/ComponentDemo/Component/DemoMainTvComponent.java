package com.example.administrator.androidtest.Test.MainTest.ComponentDemo.Component;

import android.view.View;
import android.widget.TextView;

import com.bear.libcomponent.component.ContainerComponent;
import com.example.administrator.androidtest.R;

public class DemoMainTvComponent extends ContainerComponent {

    private TextView textMainTv;

    private String originText;

    @Override
    protected void onCreate() {
        super.onCreate();
//        textMainTv = findViewById(R.id.textMainTv);
//        textMainTv.setClickable(true);
//        textMainTv.setOnClickListener(v -> getComponent(TestMinorTvComponent.class).showMinorTv());
    }

    @Override
    protected void attachView(View view) {
        super.attachView(view);
        if (view != null) {
            textMainTv = findViewById(R.id.textMainTv);
            originText = String.valueOf(textMainTv.getText());
            textMainTv.setClickable(true);
            textMainTv.setOnClickListener(v -> getComponent(DemoMinorTvComponent.class).resetMinorTv());
        }
    }

    public void showMainTv() {
        textMainTv.setText("I am mainTv");
    }

    public void resetMainTv() {
        textMainTv.setText(originText);
    }
}
