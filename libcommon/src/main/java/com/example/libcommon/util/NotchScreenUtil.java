package com.example.libcommon.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NotchScreenUtil extends AppInitUtil {

    private static String TAG = "NotchScreenUtils";

    public static final int NOTCH_IN_SCREEN_VIVO =0x00000020; //vivo手机是否有凹槽
    public static final int ROUNDED_IN_SCREEN_VOIO = 0x00000008; //vivo手机是否有圆角
    public static final int FLAG_NOTCH_SUPPORT = 0x00010000; //华为刘海屏全屏显示FLAG

    private static boolean sNotchCheck = false; //是否已经进行过刘海屏检查了
    private static boolean sIsNotchScreen = false; //是否是刘海屏手机

    private static int TYPE_NORMAL = 0;
    private static int TYPE_HUAWEI = 1;
    private static int TYPE_OPPO = 2;
    private static int TYPE_VIVO = 3;
    private static int TYPE_XIAOMI = 4;
    private static int sNotchScreenType = TYPE_NORMAL; // 刘海屏手机类型

    /**
     * 是否刘海屏手机
     * @param context
     * @return
     */
    public static boolean isNotchScreenPhone(Context context) {
        if (context == null) {
            return false;
        }
        if (!sNotchCheck) {
            sIsNotchScreen = hasNotchInHuawei(context) || hasNotchInOppo(context) || hasNotchInVivo(context) || hasNotchInXiaoMi(context); //TODO 更多刘海屏厂商手机是否刘海屏手机的判断
            sNotchCheck = true;
        }
        return sIsNotchScreen;
    }


    /**
     * XIAOMI
     * <p>
     * android.os.SystemProperties
     * public static int getInt(String key, int def)
     * <p>
     * 参数:
     * ro.miui.notch 是否刘海屏的判断，取值==1时代表是刘海屏
     *
     * @param context Context
     * @return hasNotch
     */
    public static boolean hasNotchInXiaoMi(Context context) {
        boolean hasNotch = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class systemPropertiesClass = cl.loadClass("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("getInt", String.class, int.class);
            int notch = (int)m.invoke(systemPropertiesClass, "ro.miui.notch", -1);
            if (notch == 1) {
                hasNotch = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (hasNotch) {
            sNotchScreenType = TYPE_XIAOMI;
        }
        return hasNotch;
    }



    /**
     * OPPO
     *
     * @param context Context
     * @return hasNotch
     */
    public static boolean hasNotchInOppo(Context context) {
        boolean hasNotch = false;
        try {
            hasNotch = context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
            Log.i(TAG, "hasNotchInOppo hasNotch = " + hasNotch);
        } catch (Exception e) {
            Log.e(TAG, "hasNotchInOppo Exception");
        } finally {
            if (hasNotch) {
                sNotchScreenType = TYPE_OPPO;
            }
        }
        return hasNotch;
    }

    /**
     * VIVO
     * <p>
     * android.util.FtFeature
     * public static boolean isFeatureSupport(int mask);
     * <p>
     * 参数:
     * 0x00000020表示是否有凹槽;
     * 0x00000008表示是否有圆角。
     *
     * @param context Context
     * @return hasNotch
     */
    public static boolean hasNotchInVivo(Context context) {
        boolean hasNotch = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class FtFeature = cl.loadClass("android.util.FtFeature");
            Method get = FtFeature.getMethod("isFeatureSupport",int.class);
            hasNotch = (boolean) get.invoke(FtFeature, NOTCH_IN_SCREEN_VIVO);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "hasNotchInVivo ClassNotFoundException");
        } catch (NoSuchMethodException e){
            Log.e(TAG, "hasNotchInVivo NoSuchMethodException");
        } catch (Exception e){
            Log.e(TAG, "hasNotchInVivo Exception");
        } finally {
            Log.i(TAG, "hasNotchInVivo hasNotch = " + hasNotch);
            if (hasNotch) {
                sNotchScreenType = TYPE_VIVO;
            }
        }
        return hasNotch;

    }



    /**
     * HUAWEI
     * com.huawei.android.util.HwNotchSizeUtil
     * public static boolean hasNotchInScreen()
     *
     * @param context Context
     * @return hasNotch
     */
    public static boolean hasNotchInHuawei(Context context) {
        boolean hasNotch = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            hasNotch = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (hasNotch) {
                sNotchScreenType = TYPE_HUAWEI;
            }
        }
        return hasNotch;
    }


    /**
     * 刘海屏手机申明刘海区显示全屏
     * @param context
     */
    public static void applyNotchFullScreen(Activity context) {
        Log.i(TAG, "applyNotchFullScreen");
        if (context == null) {
            return;
        }
        if (isNotchScreenPhone(context)) { //是刘海屏手机
            if (sNotchScreenType == TYPE_HUAWEI) { //如果是华为刘海屏手机，申请华为手机屏幕全屏显示
                showNotchAreaInHuaWei(context.getWindow());
            }
            //TODO 其他手机申请刘海区全屏显示,呵呵,vivo,oppo就是不提供方法
        }
    }


    public static void showNotchAreaInHuaWei(Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        setFullScreenWindowLayoutInHuaWei(window);
    }


    /**
     * 华为设置应用窗口在华为刘海屏手机使用挖孔区
     * @param window 应用页面window对象
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setFullScreenWindowLayoutInHuaWei(Window window) {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        try {
            Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(layoutParams);
            Method method = layoutParamsExCls.getMethod("addHwFlags", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |InstantiationException | InvocationTargetException e) {
            Log.e(TAG, "hw notch screen flag api error");
        } catch (Exception e) {
            Log.e(TAG, "other Exception");
        }
    }

    private static boolean sNavigationBarCoverDialog = false;
    private static boolean sNavigationBarCoverDialogCheck;

    /**
     * 导航栏覆盖在Dialog上面，使得Dialog上面的某些按钮不可点击，暂时没有什么好解决的方法
     * 暂时做个机型适配，暂时发现是华为nova 3e有这个问题
     * @return
     */
    public static boolean isNavigationBarCoverDialog() {
        if (sNavigationBarCoverDialogCheck) {
            return sNavigationBarCoverDialog;
        }
        sNavigationBarCoverDialogCheck = false;
        if ("ANE-TL00".equalsIgnoreCase(Build.MODEL)) {
            sNavigationBarCoverDialog = true;
        }
        return sNavigationBarCoverDialog;
    }

}
