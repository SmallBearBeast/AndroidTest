package com.example.administrator.androidtest.Test.BusTest;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidtest.R;
import com.example.libframework.Bus.Event;
import com.example.libframework.Bus.EventCallback;
import com.example.libframework.CoreUI.ComponentAct;
import com.example.libframework.Bus.Bus;
import com.example.liblog.SLog;

public class BusTest1Act extends ComponentAct {
    private TextView mTvTest;

    @Override
    protected int layoutId() {
        return R.layout.act_bus_test_1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTest = findViewById(R.id.tv_test);
        Bus.get().register(mCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bus.get().unRegister(mCallback);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                Bus.get().postStick(new Event("stick_event", "hello stick"));
                goAct(BusTest2Act.class);
                break;
        }
    }

    private EventCallback mCallback = new EventCallback() {
        @Override
        protected void onEvent(Event event) {
            SLog.d(TAG, "onEvent: event = " + event);
        }
    };
}
