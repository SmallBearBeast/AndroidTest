package com.example.administrator.androidtest.demo.ViewDemo.ToolbarTest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcomponent.host.ComponentActivity;
import com.example.administrator.androidtest.databinding.ActToolbarTestBinding;

import java.lang.reflect.Method;

public class ToolbarTestActivity extends ComponentActivity<ActToolbarTestBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regComponent(new ToolbarComponent(getLifecycle()));
    }

    @Override
    protected ActToolbarTestBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActToolbarTestBinding.inflate(inflater);
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
        context.startActivity(new Intent(context, ToolbarTestActivity.class));
    }
}
