package com.example.libbase.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyBoardUtil {
    private static final String TAG = "SoftKeyBoardTool";
    private static Map<Activity, KeyBoardData> keyBoardDataMap = new HashMap<>();

    private static class KeyBoardData {
        private List<KeyBoardListener> mKeyBoardListenerList = new ArrayList<>();
        private int mLastVisibleHeight = 0;
        private boolean mShowKeyBoard = false;

        private void add(KeyBoardListener keyBoardListener) {
            mKeyBoardListenerList.add(keyBoardListener);
        }

        private void onChange(boolean showKeyBoard, int bottomOffset) {
            mShowKeyBoard = showKeyBoard;
            for (KeyBoardListener listener : mKeyBoardListenerList) {
                if (listener != null) {
                    listener.onChange(showKeyBoard, bottomOffset);
                }
            }
        }
    }

    public interface KeyBoardListener {
        void onChange(boolean showKeyBoard, int bottomOffset);
    }

    public static boolean isShowKeyBoard(Activity activity) {
        KeyBoardData keyBoardData = keyBoardDataMap.get(activity);
        return keyBoardData != null && keyBoardData.mShowKeyBoard;
    }

    public static void observeKeyBoard(final Activity activity, final KeyBoardListener keyBoardListener) {
        KeyBoardData keyBoardData = keyBoardDataMap.get(activity);
        if (keyBoardData != null) {
            if (keyBoardListener != null) {
                keyBoardData.add(keyBoardListener);
            }
            return;
        }
        keyBoardData = new KeyBoardData();
        keyBoardDataMap.put(activity, keyBoardData);
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
                        Display display = activity.getWindowManager().getDefaultDisplay();
                        Point point = new Point();
                        display.getRealSize(point);
                        int bottomOffset = point.y - r.bottom;
                        Log.d(TAG, "onGlobalLayout: visibleHeight = " + visibleHeight + ", lastVisibleHeight = " + lastVisibleHeight + ", bottomOffset = " + bottomOffset + ", point.y = " + point.y);
                        if (bottomOffset > 200) {
                            finalKeyBoardData.onChange(true, bottomOffset);
                        } else {
                            finalKeyBoardData.onChange(false, bottomOffset);
                        }
                    }
                    finalKeyBoardData.mLastVisibleHeight = visibleHeight;
                }
            }
        });

        if (activity instanceof LifecycleOwner) {
            LifecycleOwner lifecycleOwner = (LifecycleOwner) activity;
            lifecycleOwner.getLifecycle().addObserver(new LifecycleEventObserver() {
                @Override
                public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                    if (Lifecycle.Event.ON_DESTROY == event) {
                        source.getLifecycle().removeObserver(this);
                        keyBoardDataMap.remove(activity);
                    }
                }
            });
        }
    }

    public static void showSoftInput(Context context, View view) {
        try {
            final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null) {
                view.requestFocus();
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        } catch (Exception e) {

        }
    }

    public static void hideSoftInput(Context context, View view) {
        try {
            final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {

        }
    }
}