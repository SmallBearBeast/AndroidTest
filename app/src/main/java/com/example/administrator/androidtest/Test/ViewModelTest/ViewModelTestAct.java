package com.example.administrator.androidtest.Test.ViewModelTest;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.TextView;

import com.bear.libcomponent.ComponentAct;
import com.bear.libcomponent.ShareVM;
import com.example.administrator.androidtest.R;
import com.example.liblog.SLog;

public class ViewModelTestAct extends ComponentAct {
    private UserVM mUserVM;
    private TextView mTextView;

    @Override
    protected int layoutId() {
        return R.layout.act_view_model;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextView = findViewById(R.id.tv_content);
        mUserVM = ViewModelProviders.of(this).get(UserVM.class);

        mUserVM.userData_1(this, new Observer<ViewModelData>() {
            @Override
            public void onChanged(@Nullable ViewModelData user) {
                SLog.d(TAG, "userData_1: user = " + user);
                mTextView.setText(user != null ? user.toString() : null);
            }
        });
        mUserVM.userData_1();

        mUserVM.userData_2();
        mUserVM.userData_2(this, new Observer<ViewModelData>() {
            @Override
            public void onChanged(@Nullable ViewModelData user) {
                SLog.d(TAG, "userData_2: user = " + user);
                mTextView.setText(user != null ? user.toString() : null);
            }
        });

        mUserVM.userData_3();
        mUserVM.userData_3(this, new Observer<ViewModelData>() {
            @Override
            public void onChanged(@Nullable ViewModelData user) {
                SLog.d(TAG, "userData_3: user = " + user);
                mTextView.setText(user != null ? user.toString() : null);
            }
        });

        initLifeCycle();
        ShareVM.put(this, "ViewModelTestAct", new ViewModelData("test", 123));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_test_1:
                mUserVM.userData_1();
                break;

            case R.id.bt_test_2:
                mUserVM.userData_2();
                break;

            case R.id.bt_test_3:
                mTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mUserVM.userData_3();
                    }
                }, 3000);
                break;

            case R.id.bt_test_4:
                ViewModelTestAct.go(this);
                break;

            case R.id.bt_test_5:
                ViewModelData data = ShareVM.get(this, "ViewModelTestAct");
                mUserVM.userData_1.setValue(data);
                break;
        }
    }

    private void initLifeCycle() {
        getLifecycle().addObserver(new GenericLifecycleObserver() {
            @Override
            public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {

            }
        });
        getLifecycle().addObserver(new LifeCycleTestObserver());
    }

    public static void go(Context context) {
        ContextCompat.startActivity(context, new Intent(context, ViewModelTestAct.class), null);
    }
}