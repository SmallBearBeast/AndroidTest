package com.example.libbase.Util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import androidx.core.text.TextUtilsCompat;
import androidx.core.view.ViewCompat;
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
}