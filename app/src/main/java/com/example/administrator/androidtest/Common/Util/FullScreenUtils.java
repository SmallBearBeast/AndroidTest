package com.example.administrator.androidtest.Common.Util;


import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class FullScreenUtils {

    /**
     * 进入沉浸式，应该与{@link #hideStatusBar(Activity)}、{@link #hideNavigationBar(Activity)}一起调用
     * @param activity 当前界面
     * @param sticky 是否为严格沉浸式
     */
    public static void enterImmersive(Activity activity, boolean sticky) {
        Window window = activity.getWindow();
        enterImmersive(window, sticky);
    }

    /**
     * 进入沉浸式，应该与{@link #hideStatusBar(Activity)}、{@link #hideNavigationBar(Activity)}一起调用
     * @param window 当前Window
     * @param sticky 是否为严格沉浸式
     */
    public static void enterImmersive(@Nullable Window window, boolean sticky) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = window.getDecorView();
            int newSysUi = decorView.getSystemUiVisibility();
            if (sticky) {
                newSysUi |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            } else {
                newSysUi |= View.SYSTEM_UI_FLAG_IMMERSIVE;
            }
            setDecorSysUiVisibility(decorView, newSysUi);
            setupSysUiListener(decorView, newSysUi);
        }
    }

    public static void exitImmersive(Activity activity) {
        Window window = activity.getWindow();
        exitImmersive(window);
    }

    public static void exitImmersive(@Nullable Window window) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = window.getDecorView();
            int newSysUi = decorView.getSystemUiVisibility();
            newSysUi &= ~View.SYSTEM_UI_FLAG_IMMERSIVE & ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            setDecorSysUiVisibility(decorView, newSysUi);
            setupSysUiListener(decorView, newSysUi);
        }
    }

    /**
     * 设置让应用内容可以绘制到系统UI区域，确保状态栏/导航栏显隐的时候，界面不跳变，一般与<br/>
     * {@link #enableTransparentStatusBar(Activity, boolean)}、<br/>
     * {@link #enableTransparentNaviBar(Activity, boolean)} <br/>
     * 配合使用
     * @param activity 当前界面
     * @param drawToStatus 状态栏区域不跳变
     * @param drawToNavi 导航栏区域不跳变
     */
    public static void enableDrawToSysUi(Activity activity, boolean drawToStatus, boolean drawToNavi) {
        Window window = activity.getWindow();
        enableDrawToSysUi(window, drawToStatus, drawToNavi);
    }

    /**
     * 设置让应用内容可以绘制到系统UI区域，确保状态栏/导航栏显隐的时候，界面不跳变，一般与<br/>
     * {@link #enableTransparentStatusBar(Activity, boolean)}、<br/>
     * {@link #enableTransparentNaviBar(Activity, boolean)} <br/>
     * 配合使用
     * @param window 当前Window
     * @param drawToStatus 状态栏区域不跳变
     * @param drawToNavi 导航栏区域不跳变
     */
    public static void enableDrawToSysUi(@Nullable Window window, boolean drawToStatus, boolean drawToNavi) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            View decorView = window.getDecorView();
            int newSysUi = decorView.getSystemUiVisibility();
            newSysUi |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (drawToStatus) {
                newSysUi |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            }
            if (drawToNavi) {
                newSysUi |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            }
            setDecorSysUiVisibility(decorView, newSysUi);
        }
    }


    public static void enableWindowDrawToSysUi(View rootView, boolean drawToStatus, boolean drawToNavi) {
        if (rootView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int newSysUi = rootView.getSystemUiVisibility();
            newSysUi |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (drawToStatus) {
                newSysUi |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            }
            if (drawToNavi) {
                newSysUi |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            }
            setDecorSysUiVisibility(rootView, newSysUi);
        }
    }

    public static void disableDrawToSysUi(Activity activity) {
        Window window = activity.getWindow();
        disableDrawToSysUi(window);
    }

    public static void disableDrawToSysUi(@Nullable Window window) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            View decorView = window.getDecorView();
            int newSysUi = decorView.getSystemUiVisibility();
            newSysUi &= ~View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    & ~View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    & ~View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            setDecorSysUiVisibility(decorView, newSysUi);
        }
    }

    /**
     * 使状态栏背景透明
     * @param activity 当前界面
     * @param lightContent 即将显示在状态栏区域的内容是否为浅色的，如果不确定，则传{@code false}即可。
     */
    public static void enableTransparentStatusBar(Activity activity, boolean lightContent) {
        Window window = activity.getWindow();
        enableTransparentStatusBar(window, lightContent);
    }

    /**
     * 使状态栏背景透明
     * @param window 当前Window
     * @param lightContent 即将显示在状态栏区域的内容是否为浅色的，如果不确定，则传{@code false}即可。
     */
    public static void enableTransparentStatusBar(@Nullable Window window, boolean lightContent) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = window.getDecorView();
            int newSysUi = decorView.getSystemUiVisibility();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (lightContent) {
                    newSysUi |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    newSysUi &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
            }
            setDecorSysUiVisibility(decorView, newSysUi);
        }
    }

    public static void disableTransparentStatusBar(Activity activity) {
        Window window = activity.getWindow();
        disableTransparentStatusBar(window);
    }

    public static void disableTransparentStatusBar(@Nullable Window window) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = window.getDecorView();
            int newSysUi = decorView.getSystemUiVisibility();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                newSysUi &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            setDecorSysUiVisibility(decorView, newSysUi);
        }
    }

    /**
     * 使导航栏背景透明
     * @param activity 当前界面
     * @param lightContent 即将显示在导航栏区域的内容是否为浅色的，如果不确定，则传{@code false}即可。
     */
    public static void enableTransparentNaviBar(Activity activity, boolean lightContent) {
        Window window = activity.getWindow();
        enableTransparentNaviBar(window, lightContent);
    }

    /**
     * 使导航栏背景透明
     * @param window 当前Window
     * @param lightContent 即将显示在导航栏区域的内容是否为浅色的，如果不确定，则传{@code false}即可。
     */
    public static void enableTransparentNaviBar(@Nullable Window window, boolean lightContent) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = window.getDecorView();
            int newSysUi = decorView.getSystemUiVisibility();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (lightContent) {
                    newSysUi |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                } else {
                    newSysUi &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                }
            }
            setDecorSysUiVisibility(decorView, newSysUi);
        }
    }

    public static void disableTransparentNaviBar(Activity activity) {
        Window window = activity.getWindow();
        disableTransparentNaviBar(window);

    }

    public static void disableTransparentNaviBar(@Nullable Window window) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = window.getDecorView();
            int newSysUi = decorView.getSystemUiVisibility();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                newSysUi &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
            setDecorSysUiVisibility(decorView, newSysUi);
        }
    }

    /**
     * 显示状态栏
     * @param activity 当前界面
     */
    public static void showStatusBar(Activity activity) {
        Window window = activity.getWindow();
        showStatusBar(window);
    }

    /**
     * 显示状态栏
     * @param window 当前Window
     */
    public static void showStatusBar(@Nullable Window window) {
        if (window == null) {
            return;
        }
        View decorView = window.getDecorView();
        int newSysUi = decorView.getSystemUiVisibility();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            newSysUi &= ~View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        setDecorSysUiVisibility(decorView, newSysUi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setupSysUiListener(decorView, newSysUi);
        }
    }

    public static void hideStatusBar(Activity activity) {
        Window window = activity.getWindow();
        hideStatusBar(window);
    }

    public static void hideStatusBar(@Nullable Window window) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            View decorView = window.getDecorView();
            int newSysUi = decorView.getSystemUiVisibility();
            newSysUi |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                newSysUi |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            setDecorSysUiVisibility(decorView, newSysUi);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setupSysUiListener(decorView, newSysUi);
            }
        }
    }

      public static void hideWindowStatusBar(View rootView) {
        if (rootView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int newSysUi = rootView.getSystemUiVisibility();
            newSysUi |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                newSysUi |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            setDecorSysUiVisibility(rootView, newSysUi);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setupSysUiListener(rootView, newSysUi);
            }
        }
    }




    /**
     * 显示导航栏
     * @param activity 当前界面
     */
    public static void showNavigationBar(Activity activity) {
        Window window = activity.getWindow();
        showNavigationBar(window);
    }

    /**
     * 显示导航栏
     * @param window 当前Window
     */
    public static void showNavigationBar(@Nullable Window window) {
        if (window == null) {
            return;
        }
        View decorView = window.getDecorView();
        int newSysUi = decorView.getSystemUiVisibility();
        newSysUi &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        setDecorSysUiVisibility(decorView, newSysUi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setupSysUiListener(decorView, newSysUi);
        }
    }

    public static void hideNavigationBar(Activity activity) {
        Window window = activity.getWindow();
        hideNavigationBar(window);
    }

    public static void hideNavigationBar(@Nullable Window window) {
        if (window == null) {
            return;
        }

        int navigationH = OsUtil.getNavigationHeight(window.getContext());
        if(navigationH <= 0){//不存在导航栏
            return;
        }

        View decorView = window.getDecorView();
        int newSysUi = decorView.getSystemUiVisibility();
        newSysUi |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            newSysUi |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        setDecorSysUiVisibility(decorView, newSysUi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setupSysUiListener(decorView, newSysUi);
        }
    }

    private static void setDecorSysUiVisibility(View decorView, int sysUiVisibility) {
        decorView.setSystemUiVisibility(sysUiVisibility);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            decorView.requestApplyInsets();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                decorView.requestFitSystemWindows();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void setupSysUiListener(final View decorView, int sysUiVisibility) {
        boolean isImmersiveSticky = (sysUiVisibility & View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) != 0;
        final boolean isStatusHidden = (sysUiVisibility & View.SYSTEM_UI_FLAG_FULLSCREEN) != 0;
        final boolean isNaviHidden = (sysUiVisibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0;
        if (isImmersiveSticky) {
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    boolean hasChanged = false;
                    int newSysUi = decorView.getSystemUiVisibility();
                    if (isStatusHidden && (visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        newSysUi |= View.SYSTEM_UI_FLAG_FULLSCREEN;
                        hasChanged = true;
                    }
                    if (isNaviHidden && (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
                        newSysUi |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                        hasChanged = true;
                    }
                    if (hasChanged) {
                        setDecorSysUiVisibility(decorView, newSysUi);
                    }
                }
            });
        } else {
            decorView.setOnSystemUiVisibilityChangeListener(null);
        }
    }

    /**
     * 进入简单粗暴的全屏样式，状态栏隐藏，且内容绘制到状态栏区域。
     * @param activity 当前界面
     */
    public static void enterSimpleFullScreen(Activity activity) {
        Window window = activity.getWindow();
        enterSimpleFullScreen(window);
    }

    /**
     * 进入简单粗暴的全屏样式，状态栏隐藏，且内容绘制到状态栏区域。
     * @param window 当前Window
     */
    public static void enterSimpleFullScreen(@Nullable Window window) {
        if (window == null) {
            return;
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 退出简单粗暴的全屏样式，状态栏显示，且内容不能绘制到状态栏区域。
     * @param activity 当前界面
     */
    public static void exitSimpleFullScreen(Activity activity) {
        Window window = activity.getWindow();
        exitSimpleFullScreen(window);
    }

    /**
     * 退出简单粗暴的全屏样式，状态栏显示，且内容不能绘制到状态栏区域。
     * @param window 当前Window
     */
    public static void exitSimpleFullScreen(@Nullable Window window) {
        if (window == null) {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void enterImmersiveFullScreen(Activity activity) {
        enterImmersiveFullScreen(activity.getWindow());
    }

    public static void enterImmersiveFullScreen(@Nullable Window window) {
        hideStatusBar(window);
        hideNavigationBar(window);
        enableDrawToSysUi(window, true, true);
        enableTransparentStatusBar(window, false);
        enableTransparentNaviBar(window, false);
    }
}