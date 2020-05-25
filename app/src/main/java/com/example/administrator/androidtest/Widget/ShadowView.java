package com.example.administrator.androidtest.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ShadowView extends View {
    private int mRadius;
    private int mLeftTopRadius;
    private int mRightTopRadius;
    private int mLeftBottomRadius;
    private int mRightBottomRadius;
    private boolean mTopShow;
    private boolean mBottomShow;
    private boolean mLeftShow;
    private boolean mRightShow;
    private int mBgColor;
    private Paint mShadowPaint;
    private Paint mBgPaint;
    private Path mPath;
    public ShadowView(Context context) {
        this(context, null);
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mShadowPaint = new Paint();
        mShadowPaint.setAntiAlias(true);
        mShadowPaint.setDither(true);
        // TRANSPARENT无法绘制出阴影
        mShadowPaint.setColor(Color.WHITE);
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setShadowLayer(10, 0, 0, Color.parseColor("#4c000000"));

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setDither(true);
        // TRANSPARENT无法绘制出阴影
        mBgPaint.setColor(Color.RED);
        mBgPaint.setStrokeWidth(3);
        mBgPaint.setStyle(Paint.Style.STROKE);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPath = new Path();
        mPath.moveTo(100, 100);
        mPath.lineTo(200, 100);
        mPath.lineTo(200, 200);
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        canvas.drawPath(mPath, mShadowPaint);
//        canvas.drawCircle(200, 200, 200, mShadowPaint);
//
//
//        mShadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//        mShadowPaint.setColor(Color.BLUE);
//        canvas.drawRoundRect(new RectF(200, 200, 500, 300), 15, 15, mShadowPaint);
//        mShadowPaint.setXfermode(null);
    }
}
