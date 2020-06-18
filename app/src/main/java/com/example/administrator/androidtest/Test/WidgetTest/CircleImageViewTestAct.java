package com.example.administrator.androidtest.Test.WidgetTest;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.CircleRefreshView.CircleImageView;
import com.example.libbase.Util.ThreadUtil;
import com.example.libframework.CoreUI.ComponentAct;

public class CircleImageViewTestAct extends ComponentAct {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.srl_content);
        refreshLayout.setColorSchemeColors(Color.YELLOW, Color.BLUE, Color.BLACK);
        refreshLayout.setSize(CircularProgressDrawable.LARGE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ThreadUtil.postOnMain(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        final CircleImageView circleImageView = findViewById(R.id.civ_circle);
        ThreadUtil.postOnMain(new Runnable() {
            @Override
            public void run() {
//                refreshLayout.setRefreshing(true);
                circleImageView.start();
            }
        }, 3000);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_circle_image_view_test;
    }
}
