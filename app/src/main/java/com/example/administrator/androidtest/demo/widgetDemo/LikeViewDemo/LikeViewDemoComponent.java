package com.example.administrator.androidtest.demo.widgetDemo.LikeViewDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;
import com.example.administrator.androidtest.widget.LikeView.LikeView;

public class LikeViewDemoComponent extends TestActivityComponent {
    private LikeView likeView;

    public LikeViewDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

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
