package com.example.administrator.androidtest.demo.ComponentDemo.Component;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bear.libcomponent.component.ViewComponent;
import com.example.administrator.androidtest.R;

public class DemoMainTvComponent extends ViewComponent {

    private TextView textMainTv;

    private String originText;

    public DemoMainTvComponent(View view) {
        super(view);
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
    protected void onAttachView(View view) {
        super.onAttachView(view);
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

    @Override
    public void onViewAttachedToWindow(@NonNull View v) {
        Log.d(TAG, "onViewAttachedToWindow() called with: v = [" + v + "]");
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull View v) {
        Log.d(TAG, "onViewDetachedFromWindow() called with: v = [" + v + "]");
    }
}
