package com.example.libbase.Util;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.*;

import androidx.annotation.ColorRes;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    int statusBarHeight = ScreenUtil.getStatusBarHeight();
                    lp.height = view.getMeasuredHeight() + statusBarHeight;
                    view.setLayoutParams(lp);
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight, view.getPaddingRight(), view.getPaddingBottom());
                }
            });
        }
    }

    private static Map<Activity, KeyBoardData> keyBoardListenerMap = new HashMap<>();

    private static class KeyBoardData {
        private List<KeyBoardListener> mKeyBoardListenerList = new ArrayList<>();
        private int mLastVisibleHeight = 0;
        private boolean mShowKeyBoard = false;

        private void add(KeyBoardListener keyBoardListener) {
            mKeyBoardListenerList.add(keyBoardListener);
        }

        private void onChange(boolean showKeyBoard, int keyBoardHeight) {
            mShowKeyBoard = showKeyBoard;
            for (KeyBoardListener listener : mKeyBoardListenerList) {
                if (listener != null) {
                    listener.onChange(showKeyBoard, keyBoardHeight);
                }
            }
        }
    }

    public interface KeyBoardListener {
        void onChange(boolean showKeyBoard, int keyBoardHeight);
    }

    public static boolean isShowKeyBoard(Activity activity) {
        KeyBoardData keyBoardData = keyBoardListenerMap.get(activity);
        return keyBoardData != null && keyBoardData.mShowKeyBoard;
    }

    public static void observeKeyBoard(final Activity activity, final KeyBoardListener keyBoardListener) {
        KeyBoardData keyBoardData = keyBoardListenerMap.get(activity);
        if (keyBoardData != null) {
            if (keyBoardListener != null) {
                keyBoardData.add(keyBoardListener);
            }
            return;
        }
        keyBoardData = new KeyBoardData();
        keyBoardListenerMap.put(activity, keyBoardData);
        if (keyBoardListener != null) {
            keyBoardData.add(keyBoardListener);
        }
        final View decorView = activity.getWindow().getDecorView();
        final KeyBoardData finalKeyBoardData = keyBoardData;
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.bottom - r.top;
                int lastVisibleHeight = finalKeyBoardData.mLastVisibleHeight;
                if (visibleHeight != lastVisibleHeight) {
                    if (lastVisibleHeight > 0) {
                        int diff = lastVisibleHeight - visibleHeight;
                        if (diff > 200) {
                            finalKeyBoardData.onChange(true, diff);
                        } else {
                            finalKeyBoardData.onChange(false, 0);
                        }
                    }
                    finalKeyBoardData.mLastVisibleHeight = visibleHeight;
                }
            }
        });

        if (activity instanceof LifecycleOwner) {
            LifecycleOwner lifecycleOwner = (LifecycleOwner) activity;
            lifecycleOwner.getLifecycle().addObserver(new GenericLifecycleObserver() {
                @Override
                public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                    if (Lifecycle.Event.ON_DESTROY == event) {
                        source.getLifecycle().removeObserver(this);
                        keyBoardListenerMap.remove(activity);
                    }
                }
            });
        }
    }

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