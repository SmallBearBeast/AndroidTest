package com.example.libbase.Util;

import androidx.annotation.StringRes;
import android.view.View;
import android.widget.Toast;

public final class ToastUtil extends AppInitUtil{
    private static Toast sToast;
    public static void showToast(String text) {
        showToast(new ToastConfig(text));
    }

    public static void showToast(@StringRes int resId) {
        showToast(new ToastConfig(resId));
    }

    public static void showToast(final ToastConfig config){
        ThreadUtil.postOnMain(new Runnable() {
            @Override
            public void run() {
                if (sToast != null) {
                    sToast.cancel();
                    sToast = null;
                }
                sToast = Toast.makeText(getApp(), "", Toast.LENGTH_SHORT);
                if(config.mText == null && config.mStringResId != -1){
                    config.mText = getString(config.mStringResId);
                }
                sToast.setText(config.mText);
                sToast.setDuration(config.mDuration);
                if(config.mGravity == 0){
                    config.mGravity = sToast.getGravity();
                }
                if(config.mXOffset == 0){
                    config.mXOffset = sToast.getXOffset();
                }
                if(config.mYOffset == 0){
                    config.mYOffset = sToast.getYOffset();
                }
                sToast.setGravity(config.mGravity, config.mXOffset, config.mYOffset);
                if(config.mView == null){
                    config.mView = sToast.getView();
                }
                sToast.setView(config.mView);
                sToast.show();
            }
        });
    }

    public static class ToastConfig{
        public String mText;
        public int mStringResId;
        public int mDuration = Toast.LENGTH_SHORT;
        public int mGravity;
        public int mXOffset;
        public int mYOffset;
        public View mView;

        public ToastConfig(String text) {
            mText = text;
        }

        public ToastConfig(int stringResId) {
            mStringResId = stringResId;
        }
    }

    private static String getString(int resId) {
        return getContext().getString(resId);
    }
}
