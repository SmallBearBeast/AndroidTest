package com.example.administrator.androidtest.Test.AspectTest;

import android.view.View;
import com.example.administrator.androidtest.R;
import com.example.libaspectj.Annotation.AsClick;
import com.example.libaspectj.Annotation.Thread.AsThread;
import com.example.libaspectj.Annotation.AsTime;
import com.example.libaspectj.Annotation.Thread.ThreadMode;
import com.example.libframework.ActAndFrag.ComponentAct;

public class AspectTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_aspect_test;
    }

    @AsClick(interval = 2000)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_1:
                testUser();
                break;

            case R.id.bt_2:
                testUser();
                break;
        }
    }

    @AsThread(mode = ThreadMode.UI)
    @AsTime
    private void testUser(){
        User user = new User();
        user.name = "zhangqing";
        user.age = 22;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static class User{
        String name;
        int age;
    }
}
