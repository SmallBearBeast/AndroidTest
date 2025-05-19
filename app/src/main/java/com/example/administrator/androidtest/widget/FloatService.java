package com.example.administrator.androidtest.widget;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.example.libcommon.Util.DensityUtil;

public abstract class FloatService extends Service {
    private View mFloatView;
    private WindowManager mWm;
    private WindowManager.LayoutParams mWmLp;

    @Override
    public void onCreate() {
        super.onCreate();
        View floatView = floatView();
        if (floatView != null) {
            mFloatView = floatView;
        } else if (layoutId() != View.NO_ID) {
            mFloatView = LayoutInflater.from(this).inflate(layoutId(), null);
        }
        if (mFloatView == null) {
            throw new RuntimeException("FloatView is null, please implement layoutId() or floatView()");
        }
        initView(mFloatView);
        mWm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWmLp = defaultLayoutParams();
    }

    /**
     * This is the right time to show float window on onStartCommand.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatWindow();
        return super.onStartCommand(intent, flags, startId);
    }


    private void showFloatWindow() {
        mWm.addView(mFloatView, mWmLp);
        mFloatView.setOnTouchListener(new FloatOnTouchListener());
    }

    private void removeFloatWindow() {
        mWm.removeView(mFloatView);
        mFloatView = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeFloatWindow();
        mWm = null;
        mWmLp = null;
    }

    protected @LayoutRes
    int layoutId() {
        return View.NO_ID;
    }

    protected View floatView() {
        return null;
    }

    protected void initView(View contentView) {

    }

    protected WindowManager.LayoutParams layoutParams() {
        return defaultLayoutParams();
    }

    protected WindowManager.LayoutParams defaultLayoutParams() {
        WindowManager.LayoutParams wmLp = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmLp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmLp.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        wmLp.format = PixelFormat.RGBA_8888;
        wmLp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmLp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        wmLp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        wmLp.gravity = Gravity.START | Gravity.TOP;
        wmLp.x = 0;
        wmLp.y = DensityUtil.getScreenHeight() / 2;
        return wmLp;
    }

    protected View getContentView() {
        return mFloatView;
    }

    private class FloatOnTouchListener implements View.OnTouchListener {

        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    mWmLp.x = mWmLp.x + movedX;
                    mWmLp.y = mWmLp.y + movedY;
                    mWm.updateViewLayout(view, mWmLp);
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    public static <T extends FloatService> void startFloatService(Activity activity, Class<T> clz) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(activity)) {
                activity.startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName())), 0);
            } else {
                activity.startService(new Intent(activity, clz));
            }
        } else {
            activity.startService(new Intent(activity, clz));
        }
    }

    public static <T extends FloatService> void stopFloatService(Activity activity, Class<T> clz) {
        activity.stopService(new Intent(activity, clz));
    }
}
