package com.example.administrator.androidtest.Common.Util;

import android.widget.Toast;

import com.example.administrator.androidtest.App;

/**
 * Created by lianzhan on 2017/12/14.
 *
 * @author lianzhan
 */

public final class ToastUtils extends AppInitUtil{
    public static final void showToast(final CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static final void showToast(final CharSequence text, final int duration) {
        UiHandlerUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(sContext, text, duration).show();
            }
        });
    }

    public static final void showToast(final int resId, final int duration) {
        UiHandlerUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(sContext, resId, duration).show();
            }
        });
    }

    public static final void showToast(final int resId, final int duration, final int gravity, final int xOffset, final int yOffset) {
        UiHandlerUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(sContext, resId, duration);
                toast.setGravity(gravity, xOffset, yOffset);
                toast.show();
            }
        });
    }

    public static final void showToast(final CharSequence str, final int duration, final int gravity, final int xOffset, final int yOffset) {
        UiHandlerUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(sContext, str, duration);
                toast.setGravity(gravity, xOffset, yOffset);
                toast.show();
            }
        });
    }
}
