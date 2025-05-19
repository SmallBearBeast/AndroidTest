package com.example.libcommon.Util;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.*;

import androidx.annotation.ColorInt;

// TODO: 2021/4/4 getDisplayCutout
// TODO: 2023/9/5 全面屏，刘海屏适配
// TODO: 2023/9/6 结合UltimateBarX和ImmersionBar整改
public class ScreenUtil extends AppInitUtil {
    public static int COLOR_DEFAULT = 0;
    /**
     * SYSTEM_UI_FLAG_IMMERSIVE_STICKY完全沉浸式，同时消失状态栏和导航栏。
     * SYSTEM_UI_FLAG_IMMERSIVE部分沉浸式，只消失状态栏。
     */

    /**
     * 正常布局：显示导航栏显示状态栏，状态栏覆盖在布局上面
     */
    public static void normalScreen(Window window, @ColorInt int statusColor, @ColorInt int navColor, View topView) {
        normalScreen(window, statusColor, navColor, true, true, topView);
    }

    public static void normalScreen(Window window, @ColorInt int statusColor, @ColorInt int navColor, boolean statusLight, boolean navLight, View topView) {
        if (window != null) {
            View decorView = window.getDecorView();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            int opt = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && statusLight) {
                opt = opt | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && navLight) {
                opt = opt | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
            decorView.setSystemUiVisibility(opt);
            topView.setPadding(topView.getPaddingLeft(), getStatusBarHeight(), topView.getPaddingRight(), topView.getPaddingBottom());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(statusColor);
                window.setNavigationBarColor(navColor);
            }
        }
    }
    /**正常布局：显示导航栏显示状态栏，状态栏覆盖在布局上面**/


    /**
     * 沉浸式全屏，下拉出现状态栏和导航栏，过一段时间消失
     */
    public static void immersiveFullScreen(Window window) {
        if (window != null) {
            View decorView = window.getDecorView();
            int opt = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(opt);
        }
    }
    /**沉浸式全屏，下拉出现状态栏和导航栏，过一段时间消失**/


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        int statusBarHeight = -1;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
    /**获取状态栏高度**/

    /**
     * 判断是否显示NavigationBar，只对普通模式起作用，对沉浸式不起作用
     *
     * @param window
     * @return
     */
    public static boolean hasNavigationBar(Window window) {
        Display display = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);
        View decorView = window.getDecorView();
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        return rect.bottom != point.y;
    }

    /**
     * 获取导航栏高度，通过navigation_bar_height获取的高度不准。
     */
    public static int getNavigationBarHeight(Window window) {
        Display display = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);
        View decorView = window.getDecorView();
        Configuration configuration = getContext().getResources().getConfiguration();
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        if (Configuration.ORIENTATION_LANDSCAPE == configuration.orientation) {
            return point.x - rect.right;
        } else {
            return point.y - rect.bottom;
        }
    }

    public static int getNavigationBarHeight() {
        int navigationBarHeight = -1;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }
    /**获取导航栏高度**/

    private static Resources getResources() {
        return getContext().getResources();
    }

    private static int getDimensionPixelSize(int id) {
        return getResources().getDimensionPixelSize(id);
    }
}