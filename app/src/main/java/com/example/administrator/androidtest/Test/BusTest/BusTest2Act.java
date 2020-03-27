package com.example.administrator.androidtest.Test.BusTest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MemoryLeakTest.FirstAct;
import com.example.libbase.Util.CollectionUtil;
import com.example.libframework.Bus.EventCallback;
import com.example.libframework.CoreUI.ComponentAct;
import com.example.libframework.Bus.Bus;
import com.example.libframework.Bus.Event;
import com.example.liblog.SLog;

import java.util.Set;

public class BusTest2Act extends ComponentAct {
    private TextView mTvTest;

    @Override
    protected int layoutId() {
        return R.layout.act_commom_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTest = findViewById(R.id.tv_text_1);
        Bus.get().register(mCallback);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                Bus.get().post(new Event("normal_event", "normal_event"));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bus.get().unRegister(mCallback);
    }

    private EventCallback mCallback = new EventCallback() {
        @Override
        protected void onEvent(Event event) {
            SLog.d(TAG, "onEvent: event = " + event);
        }

        @Override
        protected Set<String> eventKeySet() {
            return CollectionUtil.asSet("stick_event");
        }
    };

    public static void go(Context context) {
        ContextCompat.startActivity(context, new Intent(context, BusTest2Act.class), null);
    }
}
