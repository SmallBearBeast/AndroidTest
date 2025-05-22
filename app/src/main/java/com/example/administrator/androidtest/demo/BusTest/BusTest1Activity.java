package com.example.administrator.androidtest.demo.BusTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bear.libcommon.bus.Bus;
import com.bear.libcommon.bus.Event;
import com.bear.libcommon.bus.EventCallback;
import com.bear.libcomponent.component.ComponentActivity;
import com.bear.liblog.SLog;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActCommomTestBinding;

public class BusTest1Activity extends ComponentActivity<ActCommomTestBinding> {
    @Override
    protected ActCommomTestBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActCommomTestBinding.inflate(inflater);
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
