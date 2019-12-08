package com.example.libbase.Util;

import android.app.Application;
import android.content.Context;
import android.support.annotation.IntDef;

public class EnvUtil extends AppInitUtil{
    public static final int DEBUG = 1;
    public static final int RELEASE = 2;
    public static final int ALPHA = 3;

    @IntDef(value = {DEBUG, RELEASE, ALPHA})
    public @interface Env{}

    private static int sAppEnv = DEBUG;

    public static boolean isAppEnv(@Env int appEnv){
        return sAppEnv == appEnv;
    }

    public static void setAppEnv(@Env int appEnv){
        sAppEnv = appEnv;
    }

    //单例不可持有
    public static Context getContext(){
        return getContext() != null ? getContext() : getApp();
    }

    //单例可持有
    public static Application getApp(){
        return getApp();
    }
}
