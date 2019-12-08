package com.example.administrator.androidtest.Test.BusTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.androidtest.R;
import com.example.libframework.ActAndFrag.ComponentAct;
import com.example.libframework.Bus.BusProvider;
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
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvTest = findViewById(R.id.tv_test);
        BusProvider.getLocal().register(mBusEvent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getLocal().unregister(mBusEvent);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                startActivity(BusTest2Act.class, new Bundle());
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
            if(event.equals("Stick")){
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
