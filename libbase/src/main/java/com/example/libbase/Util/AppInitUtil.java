package com.example.libbase.Util;

import android.app.Application;
import android.content.Context;

public abstract class AppInitUtil {

    protected static Context sContext;

    private static Application sApplication;

    public static void init(Context context){
        sContext = context;
    }

    protected static Context getContext(){
        return sContext != null ? sContext : sApplication;
    }
}
