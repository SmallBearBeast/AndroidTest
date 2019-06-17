package com.example.administrator.androidtest.Common.Util.Core;

import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.util.DisplayMetrics;

import com.example.administrator.androidtest.Common.Util.AppInitUtil;

public class ResourceUtil extends AppInitUtil {

    public static Resources getResources() {
        return sContext.getResources();
    }

    public static AssetManager getAssets() {
        return sContext.getAssets();
    }

    public static String getString(int resId) {
        return sContext.getString(resId);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }

    public static ContentResolver getContentResolver() {
        return sContext.getContentResolver();
    }

    public static int getColor(int id) {
        return getResources().getColor(id);
    }

    /**
     * 长度资源id转px(dp -> px)返回浮点数
     */
    public static float getDimension(int id) {
        return getResources().getDimension(id);
    }

    /**
     * 长度资源id转px(dp -> px)返回整数
     */
    public static int getDimensionPixelSize(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return getResources().getDrawable(id);
    }

    /**
     * 格式化字符串
     */
    public static String getString(int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    public static Uri getResourceUri(int resId) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getResources().getResourcePackageName(resId) + '/' +
                getResources().getResourceTypeName(resId) + '/' +
                getResources().getResourceEntryName(resId));
    }
}
