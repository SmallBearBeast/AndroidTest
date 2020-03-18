package com.example.libbase.Util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import androidx.core.text.TextUtilsCompat;
import androidx.core.view.ViewCompat;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

public class OtherUtil extends AppInitUtil {
    public boolean isRtl() {
        return TextUtilsCompat.getLayoutDirectionFromLocale(
                getContext().getResources().getConfiguration().locale) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

    public static String keepLtr(String text, boolean ltr) {
        String mask = ltr ? "\u200e" : "\u200f";
        return mask + text + mask;
    }

    public static void rtlImageView(ImageView iv, int drawableId) {
        Resources resources = iv == null ? null : iv.getResources();
        if (resources == null) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableId);
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1, 0.5f, 0.5f);
        iv.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false));
    }

    public static void copyToClipboard(final String label, final String text) {
        ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText(label, text);
        if(manager != null) {
            try {
                manager.setPrimaryClip(data);
            } catch (Exception e){

            }
        }
    }

    public static void showSoftKeyboard(final Context context, final View view) {
        try {
            view.requestFocus();
            final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        } catch (NullPointerException e) {

        }
    }
}
