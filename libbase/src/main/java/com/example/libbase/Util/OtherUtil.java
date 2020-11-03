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

import java.util.ArrayList;
import java.util.List;

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

    public static void copyToClipboard(String label, String text) {
        ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText(label, text);
        if(manager != null) {
            try {
                manager.setPrimaryClip(data);
            } catch (Exception e){

            }
        }
    }

    /**
     * obj转为T类型
     */
    public static <T> T toT(Object obj, Class<T> clz){
        if(obj.getClass().isAssignableFrom(clz)){
            return (T) obj;
        }
        return null;
    }
    /**obj转为T类型**/


    /**
     * list转为T范型list
     */
    public static <T> List<T> toListT(List list, Class<T> clz){
        List<T> tList = new ArrayList<>();
        for (Object obj : list) {
            tList.add(toT(obj, clz));
        }
        return tList;
    }
    /**list转为T范型list**/
}
