package com.example.libbase.Util;

import android.app.Application;
import android.content.Context;

public abstract class AppInitUtil {

    private static Context sContext;

    private static Application sApplication;

    public static void init(Context context){
        sContext = context;
    }

    public static void init(Application application){
        sApplication = application;
    }

    // 获取上下文信息，优先返回当前Context。
    protected static Context getContext(){
        return sContext != null ? sContext : sApplication;
    }

    //单例可持有
    protected static Application getApp(){
        return sApplication;
    }
}
