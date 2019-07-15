package com.example.administrator.androidtest.Common.Util.Other;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import com.android.internal.util.Preconditions;
import com.example.administrator.androidtest.Common.Util.Core.AppUtil;
import com.example.administrator.androidtest.Common.Util.Core.MainThreadUtil;

import java.util.List;

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
}
