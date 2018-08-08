package com.example.administrator.androidtest;

import android.app.Application;

import com.example.administrator.androidtest.frag.visibility.BaseVisiableFrag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {

    public static Map<String, Boolean> FragVisibiableMap = new HashMap<>();

    public static BaseVisiableFrag.FragVisiableListener fragVisiableListener = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
