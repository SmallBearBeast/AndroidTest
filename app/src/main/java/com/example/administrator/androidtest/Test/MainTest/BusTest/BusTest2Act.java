package com.example.administrator.androidtest.Test.MainTest.BusTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.CollectionUtil;
import com.example.libframework.Bus.EventCallback;
import com.example.libframework.Bus.Bus;
import com.example.libframework.Bus.Event;
import com.example.liblog.SLog;

import java.util.Set;

public class BusTest2Act extends ComponentAct {

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
                Bus.get().post(new Event("normal_event", "normal_event"));
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

        @NonNull
        @Override
        protected Set<String> eventKeySet() {
            return CollectionUtil.asSet("stick_event");
        }
    };

    public static void start(Context context) {
        ContextCompat.startActivity(context, new Intent(context, BusTest2Act.class), null);
    }
}
