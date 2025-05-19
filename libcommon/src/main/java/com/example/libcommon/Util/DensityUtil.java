package com.example.libcommon.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
/**
 * 1.尺寸转换工具类
 * 2.支持sp dp px之间转换
 * 3.获取屏幕长宽(传入比例)
 */
public class DensityUtil extends AppInitUtil {
    public static int dp2Px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2Dp(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2Sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static int getScreenWidth() {
        DisplayMetrics dm = getDisplayMetrics(getContext());
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics dm = getDisplayMetrics(getContext());
        return dm.heightPixels;
    }

    /**
     * 按比值获取高度
     */
    public static int getScreenHeightByPersent(float persent) {
        return (int) (getScreenHeight() * persent);
    }
    /**按比值获取高度**/


    /**
     * 按照比值获取宽度
     */
    public static int getScreenWidthByPersent(float persent){
        return (int) (getScreenWidth() * persent);
    }
    /**按照比值获取宽度**/
}
