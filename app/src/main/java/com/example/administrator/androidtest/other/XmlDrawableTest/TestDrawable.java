package com.example.administrator.androidtest.other.XmlDrawableTest;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

public class TestDrawable extends Drawable {

    private static final String TAG = "TestDrawable";
    private Paint mPaint;

    public TestDrawable() {
        mPaint = new Paint();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(getBounds(), mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(0, 0, 100, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        Log.i(TAG, "setAlpha(): " + "alpha = " + alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        Log.i(TAG, "setColorFilter(): " + "colorFilter = " + colorFilter);
    }

    @Override
    public int getOpacity() {
        Log.i(TAG, "getOpacity(): ");
        return PixelFormat.OPAQUE;
    }
}
