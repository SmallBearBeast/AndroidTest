package com.example.administrator.androidtest.demo.BusTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.view.View;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.bear.libcommon.bus.Event;
import com.bear.libcommon.bus.EventCallback;
import com.bear.libcommon.bus.Bus;
import com.bear.liblog.SLog;

public class BusTest1Activity extends ComponentActivity {
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
                BusTest2Activity.start(this);
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

    public static void start(Context context) {
        ContextCompat.startActivity(context, new Intent(context, BusTest1Activity.class), null);
    }
}
