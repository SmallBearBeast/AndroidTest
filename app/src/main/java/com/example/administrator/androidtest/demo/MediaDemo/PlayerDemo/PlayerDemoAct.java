package com.example.administrator.androidtest.demo.MediaDemo.PlayerDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;

public class PlayerDemoAct extends ComponentAct {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new PlayerComponent(getLifecycle()));
    }

    @Override
    protected int layoutId() {
        return R.layout.act_player_demo;
    }

    public static void go(Context context) {
        Intent intent = new Intent(context, PlayerDemoAct.class);
        context.startActivity(intent);
    }
}
