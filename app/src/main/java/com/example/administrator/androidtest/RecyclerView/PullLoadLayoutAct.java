package com.example.administrator.androidtest.RecyclerView;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.androidtest.BaseAct;
import com.example.administrator.androidtest.BaseViewSetAct;
import com.example.administrator.androidtest.R;

public class PullLoadLayoutAct extends BaseViewSetAct {
    private RecyclerView rvUser;
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        rvUser = findViewById(R.id.rv_user);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        rvUser.setItemAnimator(new DefaultItemAnimator());
        rvUser.setAdapter(new UserAdapter());
    }

    @Override
    protected int layoutId() {
        return R.layout.widget_pull_drop_layout;
    }
}
