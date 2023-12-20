package com.example.libbase.Util;

import android.app.Application;
import android.content.Context;

public abstract class AppInitUtil {

    private static Context sContext;

    private static Application sApplication;

    public static void init(Context context){
        sContext = context;
        if (context instanceof Application) {
            sApplication = (Application) context;
        }
    }

    // 获取上下文信息，优先返回当前Context。
    protected static Context getContext(){
        return sContext != null ? sContext : sApplication;
    }
}
