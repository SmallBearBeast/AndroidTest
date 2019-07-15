package com.example.administrator.androidtest.Common.Util.Core;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.androidtest.Common.Util.AppInitUtil;

public final class ToastUtil extends AppInitUtil {
    private static Toast sToast;
    public static final void showToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static final void showToast(int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    public static final void showToast(final CharSequence text, final int duration) {
        MainThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                checkToast();
                sToast.setText(text);
                sToast.setDuration(duration);
                sToast.show();
            }
        });
    }

    public static final void showToast(final int resId, final int duration) {
        MainThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                checkToast();
                sToast.setText(resId);
                sToast.setDuration(duration);
                sToast.show();
            }
        });
    }

    public static final void showToast(final ToastConfig config){
        MainThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                checkToast();
                if(config.mText == null && config.mStringResId != -1){
                    config.mText = ResourceUtil.getString(config.mStringResId);
                }
                sToast.setText(config.mText);
                sToast.setDuration(config.mDuration);
                sToast.setGravity(config.mGravity, config.mXOffset, config.mYOffset);
                sToast.setView(config.mView);
                sToast.show();
            }
        });
    }

    private static void checkToast(){
        if(sToast == null){
            sToast = Toast.makeText(AppUtil.getApp(), "", Toast.LENGTH_SHORT);
        }
    }

    public static class ToastConfig{
        public String mText;
        public int mStringResId;
        public int mDuration = Toast.LENGTH_SHORT;
        public int mGravity = Gravity.CENTER;
        public int mXOffset;
        public int mYOffset;
        public View mView;
    }
}
