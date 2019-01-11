package com.example.administrator.androidtest.Common.Util;

import android.content.Context;

public abstract class AppInitUtil {

    protected static Context sContext;

    public static void init(Context context){
        sContext = context;
    }
}
