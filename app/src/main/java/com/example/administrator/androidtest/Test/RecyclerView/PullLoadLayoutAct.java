package com.example.administrator.androidtest.Test.RecyclerView;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administrator.androidtest.R;
import com.example.libframework.ActAndFrag.ComponentAct;

public class PullLoadLayoutAct extends ComponentAct {
    private RecyclerView rvUser;
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
//        rvUser = findViewById(R.id.rv_user);
//        rvUser.setLayoutManager(new LinearLayoutManager(this));
//        rvUser.setItemAnimator(new DefaultItemAnimator());
//        rvUser.setAdapter(new UserAdapter());
    }

    @Override
    protected int layoutId() {
        return R.layout.widget_pull_drop_layout;
    }
}
