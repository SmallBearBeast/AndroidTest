package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.LikeViewDemo;

import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;
import com.example.administrator.androidtest.Widget.LikeView.LikeView;

public class LikeViewDemoComponent extends TestActivityComponent {
    private LikeView likeView;

    @Override
    protected void onCreate() {
        super.onCreate();
        likeView = findViewById(R.id.likeView);
        setOnClickListener(this, R.id.likeView);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.likeView) {
            changeLikeView();
        }
    }

    private void changeLikeView() {
        if (likeView.isLike()) {
            likeView.like();
        } else {
            likeView.unLike();
        }
    }
}
