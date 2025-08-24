package com.example.administrator.androidtest.demo.ViewDemo.RefreshViewDemo;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bear.libcomponent.host.ComponentActivity;
import com.example.administrator.androidtest.databinding.WidgetPullDropLayoutBinding;

public class PullLoadLayoutActivity extends ComponentActivity<WidgetPullDropLayoutBinding> {
    private RecyclerView rvUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        rvUser = findViewById(R.id.rv_user);
//        rvUser.setLayoutManager(new LinearLayoutManager(this));
//        rvUser.setItemAnimator(new DefaultItemAnimator());
//        rvUser.setAdapter(new UserAdapter());
    }

    @Override
    protected WidgetPullDropLayoutBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return WidgetPullDropLayoutBinding.inflate(inflater);
    }
}
