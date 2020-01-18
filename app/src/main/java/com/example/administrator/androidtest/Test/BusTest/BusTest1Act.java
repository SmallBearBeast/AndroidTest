package com.example.administrator.androidtest.Test.BusTest;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidtest.R;
import com.example.libframework.CoreUI.ComponentAct;
import com.example.libframework.Bus.Bus;
import com.example.libframework.Bus.IBus;

import java.util.ArrayList;
import java.util.List;

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
        Bus.get().register(mBusEvent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bus.get().unregister(mBusEvent);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                goAct(BusTest2Act.class, new Bundle(), null);
                break;
        }
    }

    private IBus.OnBusEvent mBusEvent = new IBus.OnBusEvent() {
        @Override
        protected void onBusEvent(String event, @Nullable Bundle extras) {
            Log.d(TAG, "onBusEvent: event = " + event + " extras = " + extras);
        }

        @Override
        protected void onStickEvent(String event, @Nullable Bundle extras) {
            Log.d(TAG, "onStickEvent: event = " + event + " extras = " + extras);
            if (event.equals("Stick")) {
                mTvTest.setText("Stick");
            }
        }

        @Override
        protected int stickId() {
            return 100;
        }

        @Override
        protected List<String> events() {
            List<String> list = new ArrayList<>();
            list.add("Hello");
            return list;
        }

        @Override
        protected List<String> stickEvents() {
            List<String> list = new ArrayList<>();
            list.add("Stick");
            return list;
        }
    };
}
