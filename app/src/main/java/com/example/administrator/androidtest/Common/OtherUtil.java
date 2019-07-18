package com.example.administrator.androidtest.Common;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.example.libbase.Util.AppUtil;
import com.example.libbase.Util.MainThreadUtil;

@SuppressWarnings("unchecked")
public class OtherUtil {
    public static void copyToClipboard(String text) {
        copyToClipboard("text", text);
    }

    public static void copyToClipboard(final String label, final String text) {
        MainThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                ClipboardManager manager = (ClipboardManager) AppUtil.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText(label, text);
                if(manager != null) {
                    try {
                        manager.setPrimaryClip(data);
                    }catch (Exception e){

                    }
                }
            }
        });
    }

    public static void showSoftKeyboard(final Context context, final View view) {
        MainThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                try {
                    view.requestFocus();
                    final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null) {
                        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                    }
                } catch (NullPointerException e) {

                }
            }
        });
    }
}
