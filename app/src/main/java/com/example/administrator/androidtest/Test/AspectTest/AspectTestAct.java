package com.example.administrator.androidtest.Test.AspectTest;

import android.view.View;
import com.example.administrator.androidtest.R;
import com.example.libframework.ActAndFrag.ComponentAct;
import com.example.liblog.SLog;

public class AspectTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_aspect_test;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_1:
                testUser();
                break;

            case R.id.bt_2:

                break;
        }
    }

    private void testUser(){
        User user = new User();
        user.name = "zhangqing";
        user.age = 22;
        SLog.i(TAG, user.name);
        AspectDo.doLog(user);
    }

    public static class User{
        String name;
        int age;
    }
}
