package com.example.libbase.Util;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.*;

import androidx.annotation.ColorRes;

public class ScreenUtil extends AppInitUtil {

    private static int COLOR_ID_NONE = -1;
    /**
     * SYSTEM_UI_FLAG_IMMERSIVE_STICKY完全沉浸式，同时消失状态栏和导航栏。
     * SYSTEM_UI_FLAG_IMMERSIVE部分沉浸式，只消失状态栏。
     */


    /**
     * 正常布局：显示导航栏显示状态栏，状态栏覆盖在布局上面
     */
    public static void normalScreen(Window window, @ColorRes int statusColorId, @ColorRes int navColorId, View view) {
        normalScreen(window, statusColorId, navColorId, true, true, view);
    }

    public static void normalScreen(Window window, @ColorRes int statusColorId, @ColorRes int navColorId, boolean statusLight, boolean navLight, View view) {
        if (window != null) {
            View decorView = window.getDecorView();
            if (decorView != null) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                int opt = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (statusLight) {
                        opt = opt | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (navLight) {
                        opt = opt | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                    }
                }
                decorView.setSystemUiVisibility(opt);
                fitStatusBar(view);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (statusColorId != COLOR_ID_NONE) {
                    window.setStatusBarColor(getColor(statusColorId));
                }
                if (navColorId != COLOR_ID_NONE) {
                    window.setNavigationBarColor(getColor(navColorId));
                }
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
            if (decorView != null) {
                int opt = View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                decorView.setSystemUiVisibility(opt);
            }
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
    public static boolean checkNavigationBarShow(Window window) {
        Display display = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);
        View decorView = window.getDecorView();
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        return rect.bottom != point.y;
    }

    /**
     * 获取导航栏高度
     */
    public static int getNavigationBarHeight() {
        int navigationBarHeight = -1;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }
    /**获取导航栏高度**/

    /**
     * 填充状态栏高度
     */
    private static void fitStatusBar(final View view) {
        if (view != null) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    ViewGroup.LayoutParams lp = view.getLayoutParams();
                    lp.height = view.getMeasuredHeight() + ScreenUtil.getStatusBarHeight();
                    view.setLayoutParams(lp);
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + ScreenUtil.getStatusBarHeight(), view.getPaddingRight(), view.getPaddingBottom());
                }
            });
        }
    }

    /**
     * 填充状态栏高度
     **/

    private static int getColor(int id) {
        return getResources().getColor(id);
    }

    private static Resources getResources() {
        return getContext().getResources();
    }

    private static int getDimensionPixelSize(int id) {
        return getResources().getDimensionPixelSize(id);
    }
}