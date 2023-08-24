package com.example.administrator.androidtest.Test.MainTest.AspectTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.libaspectj.Annotation.AsClick;
import com.example.libaspectj.Annotation.Thread.AsThread;
import com.example.libaspectj.Annotation.AsTime;
import com.example.libaspectj.Annotation.Thread.ThreadMode;

public class AspectTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_commom_test;
    }

    @SuppressLint("NonConstantResourceId")
    @AsClick(interval = 2000)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                testUser();
                break;

            default:
                break;
        }
    }

    @AsThread(mode = ThreadMode.UI)
    @AsTime
    private void testUser() {
        User user = new User();
        user.name = "zhangqing";
        user.age = 22;
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AspectTestAct.class);
        context.startActivity(intent);
    }

    private static class User {
        String name;
        int age;
    }
}
