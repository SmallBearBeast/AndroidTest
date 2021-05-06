package com.example.libbase.Util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtil extends AppInitUtil {
    public static boolean copy(String text) {
        ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("label", text);
        if(manager != null) {
            try {
                manager.setPrimaryClip(data);
                return true;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String paste() {
        ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = manager.getPrimaryClip();
        String text = "";
        if (clipData != null) {
            ClipData.Item item = clipData.getItemAt(0);
            if (item != null) {
                text = String.valueOf(item.getText());
            }
        }
        return text;
    }
}
