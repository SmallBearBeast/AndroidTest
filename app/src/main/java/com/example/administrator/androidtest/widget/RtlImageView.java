package com.example.administrator.androidtest.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RtlImageView extends ImageView {
    public RtlImageView(Context context) {
        super(context);
    }

    public RtlImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            Matrix matrix = new Matrix();
            matrix.postScale(-1, 1, 0.5f, 0.5f);
            Bitmap output = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
            super.setImageDrawable(new BitmapDrawable(getResources(), output));
        } else {
            super.setImageDrawable(drawable);
        }
    }
}
