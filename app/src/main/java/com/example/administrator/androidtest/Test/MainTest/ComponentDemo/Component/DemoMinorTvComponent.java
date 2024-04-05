package com.example.administrator.androidtest.Test.MainTest.ComponentDemo.Component;

import android.view.View;
import android.widget.TextView;

import com.bear.libcomponent.component.ViewComponent;
import com.example.administrator.androidtest.R;

public class DemoMinorTvComponent extends ViewComponent {

    private TextView textMinorTv;

    private String originText;

    public DemoMinorTvComponent(View view) {
        super(view);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
//        textMinorTv = findViewById(R.id.textMinorTv);
//        textMinorTv.setClickable(true);
//        textMinorTv.setOnClickListener(v -> getComponent(TestMainTvComponent.class).showMainTv());
    }

    @Override
    protected void onAttachView(View view) {
        super.onAttachView(view);
        if (view != null) {
            textMinorTv = findViewById(R.id.textMinorTv);
            originText = String.valueOf(textMinorTv.getText());
            textMinorTv.setClickable(true);
            textMinorTv.setOnClickListener(v -> getComponent(DemoMainTvComponent.class).resetMainTv());
        }
    }

    public void showMinorTv() {
        textMinorTv.setText("I am minorTv");
    }

    public void resetMinorTv() {
        textMinorTv.setText(originText);
    }
}
