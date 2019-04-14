package com.example.administrator.androidtest.Common.Util.Other;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.androidtest.Common.Util.AppInitUtil;
import com.example.administrator.androidtest.Common.Util.Core.CollectionUtil;

import java.lang.reflect.Method;
import java.util.List;


/***
 * Description:
 * Creator: wangwei7@bigo.sg
 * Date:2018-02-27 05:34:31 PM
 ***/
public final class ServiceUtil extends AppInitUtil {
    private static final String TAG = "ComponentUtils";

    /***
     * Trying to fix: java.lang.SecurityException: Unable to find app for caller
     * android.app.ApplicationThreadProxy@42cc0b90 (pid=1036) when starting service Intent
     * { act=com.yy.yymeet.ACTION_RECOMMEND_COMMON_CONTACT
     *   cmp=com.yy.yymeet/com.yy.iheima.fgservice.FgWorkService }
     * @param it
     */
    public static void startService(Intent it) {
        try {
            sContext.startService(it);
        } catch (Exception ex) {
            Log.e(TAG, "startServiceQuietly failed", ex);
        }
    }

    /**
     * 判断该Activity是否有被销毁
     *
     * @param activity
     * @return true 未被销毁 false 被销毁
     */
    public static boolean isActivityOK(Activity activity) {
        if (activity == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed()) {
                return false;
            }
        }

        if (activity.isFinishing()) {
            return false;
        }

        return true;
    }

    /**
     * 让服务成为前台服务，防止被杀进程
     *
     * @throws throws
     */
    public static void startForegroundService(Service service) {
        try {
            Method method = ReflectUtil.findMethod(Service.class, "startForeground",
                    int.class, Notification.class);
            method.invoke(service, 1024, new Notification());
            return;
        } catch (Throwable e) {
        }
        try {
            Method method = ReflectUtil.findMethod(Service.class, "setForeground",
                    new Class[]{boolean.class});
            method.invoke(service, true);
        } catch (Throwable e) {
        }
    }


    /**
     * 判断服务是否启动
     *
     * @param name String
     * @return boolean
     */
    public static boolean isServiceRunning(@NonNull String name) {
        ActivityManager manager = (ActivityManager) sContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        List<ActivityManager.RunningServiceInfo> infos = manager.getRunningServices(Integer.MAX_VALUE);
        if (CollectionUtil.isEmpty(infos)) {
            return false;
        }
//        int uid = ProcessUtil.getUid();
        int uid = 0;
        if (uid <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo service : infos) {
            // 添加Uid验证, 防止服务重名, 当前服务无法启动
            if (uid == service.uid && TextUtils.equals(name, service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
