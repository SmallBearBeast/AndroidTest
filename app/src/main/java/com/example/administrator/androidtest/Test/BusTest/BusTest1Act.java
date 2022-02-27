package com.example.administrator.androidtest.Test.BusTest;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.libframework.Bus.Event;
import com.example.libframework.Bus.EventCallback;
import com.example.libframework.Bus.Bus;
import com.example.liblog.SLog;

public class BusTest1Act extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_commom_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bus.get().register(this, eventCallback);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                Bus.get().postStick(new Event("stick_event", "hello stick"));
                BusTest2Act.go(this);
                break;

            default:
                break;
        }
    }

    private final EventCallback eventCallback = new EventCallback() {
        @Override
        protected void onEvent(@NonNull Event event) {
            SLog.d(TAG, "onEvent: event = " + event);
        }
    };
}
