package com.example.administrator.androidtest.Test.MainTest.ViewDemo.ToolbarTest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;

import java.lang.reflect.Method;

public class ToolbarTestAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regActComponent(new ToolbarComponent(getLifecycle()));
    }

    @Override
    protected int layoutId() {
        return R.layout.act_toolbar_test;
    }

    // 只对setSupportActionBar起作用，让菜单同时显示图标和文字。
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ToolbarTestAct.class));
    }
}
