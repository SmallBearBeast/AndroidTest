package com.example.administrator.androidtest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;

public class ShadowLayout extends FrameLayout {
    private boolean mTopShow;
    private boolean mBottomShow;
    private boolean mLeftShow;
    private boolean mRightShow;
    private int mBgColor = Color.WHITE;
    private int mShadowColor = Color.parseColor("#80000000");
    private int mCornerRadius = 0;
    private int mShadowOffset = 0;
    private int mViewWidth = 0;
    private int mViewHeight = 0;
    private Paint mShadowPaint;
    private Paint mBgPaint;
    private Path mShapePath = new Path();

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypeArray(context, attrs);
        initPaint();
        adjustPadding();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
    }

    private void initTypeArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        mTopShow = typedArray.getBoolean(R.styleable.ShadowLayout_sl_top_show, false);
        mBottomShow = typedArray.getBoolean(R.styleable.ShadowLayout_sl_bottom_show, false);
        mLeftShow = typedArray.getBoolean(R.styleable.ShadowLayout_sl_left_show, false);
        mRightShow = typedArray.getBoolean(R.styleable.ShadowLayout_sl_right_show, false);
        mBgColor = typedArray.getColor(R.styleable.ShadowLayout_sl_bg_color, Color.WHITE);
        mShadowColor = typedArray.getColor(R.styleable.ShadowLayout_sl_shadow_color, Color.parseColor("#80000000"));
        mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.ShadowLayout_sl_corner_radius, 0);
        mShadowOffset = typedArray.getDimensionPixelSize(R.styleable.ShadowLayout_sl_shadow_offset, 0);
        mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.ShadowLayout_sl_corner_radius, 0);
        typedArray.recycle();
    }

    private void initPaint() {
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setDither(true);
        // TRANSPARENT无法绘制出阴影
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStyle(Paint.Style.FILL);

        mShadowPaint = new Paint();
        mShadowPaint.setAntiAlias(true);
        mShadowPaint.setDither(true);
        mShadowPaint.setColor(mBgColor);
        mShadowPaint.setStyle(Paint.Style.FILL);
    }

    private void adjustPadding() {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        if (mTopShow) {
            paddingTop = paddingTop + mShadowOffset;
        } else if (mBottomShow) {
            paddingBottom = paddingBottom + mShadowOffset;
        } else if (mLeftShow) {
            paddingLeft = paddingLeft + mShadowOffset;
        } else if (mRightShow) {
            paddingRight = paddingRight + mShadowOffset;
        } else {
            paddingTop = paddingTop + mShadowOffset;
            paddingBottom = paddingBottom + mShadowOffset;
            paddingLeft = paddingLeft + mShadowOffset;
            paddingRight = paddingRight + mShadowOffset;
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mViewWidth == 0 || mViewWidth != oldw) {
            mViewWidth = w;
        }
        if (mViewHeight == 0 || mViewHeight != oldh) {
            mViewHeight = h;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        updateBgPath();
        drawBg(canvas);
        drawShadow(canvas);
    }

    private void updateBgPath() {
        mShapePath.reset();
        if (mTopShow) {
            float[] radiiBg = new float[]{
                    mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, 0, 0, 0, 0
            };
            mShapePath.addRoundRect(new RectF(0, 0, mViewWidth, mViewHeight), radiiBg, Path.Direction.CW);
        } else if (mBottomShow) {
            float[] radiiBg = new float[]{
                    0, 0, 0, 0, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius
            };
            mShapePath.addRoundRect(new RectF(0, 0, mViewWidth, mViewHeight), radiiBg, Path.Direction.CW);
        } else if (mLeftShow) {
            float[] radiiBg = new float[]{
                    mCornerRadius, mCornerRadius, 0, 0, 0, 0, mCornerRadius, mCornerRadius
            };
            mShapePath.addRoundRect(new RectF(0, 0, mViewWidth, mViewHeight), radiiBg, Path.Direction.CW);
        } else if (mRightShow) {
            float[] radiiBg = new float[]{
                    0, 0, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, 0, 0
            };
            mShapePath.addRoundRect(new RectF(0, 0, mViewWidth, mViewHeight), radiiBg, Path.Direction.CW);
        } else {
            float[] radiiBg = new float[]{
                    mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius
            };
            mShapePath.addRoundRect(new RectF(mShadowOffset, mShadowOffset, mViewWidth - mShadowOffset, mViewHeight - mShadowOffset), radiiBg, Path.Direction.CW);
        }
    }

    private void drawShadow(Canvas canvas) {
        canvas.save();
        translate(canvas);
        mShadowPaint.setShadowLayer(mShadowOffset, 0, 0, mShadowColor);
        canvas.drawPath(mShapePath, mShadowPaint);
        mShadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        mShadowPaint.clearShadowLayer();
        int offset = mCornerRadius == 0 ? mShadowOffset : mCornerRadius;
        if (mTopShow) {
            canvas.drawRect(new RectF(0, offset, mViewWidth, mViewHeight), mShadowPaint);
        } else if (mBottomShow) {
            canvas.drawRect(new RectF(0, 0, mViewWidth - offset, mViewHeight - offset), mShadowPaint);
        } else if (mLeftShow) {
            canvas.drawRect(new RectF(offset, 0, mViewWidth, mViewHeight), mShadowPaint);
        } else if (mRightShow) {
            canvas.drawRect(new RectF(0, 0, mViewWidth - offset, mViewHeight), mShadowPaint);
        }
        mShadowPaint.setXfermode(null);
        canvas.restore();
    }

    private void drawBg(Canvas canvas) {
        canvas.save();
        translate(canvas);
        canvas.drawPath(mShapePath, mBgPaint);
        canvas.restore();
    }

    private void translate(Canvas canvas) {
        if (mTopShow) {
            canvas.translate(0, mShadowOffset);
        } else if (mBottomShow) {
            canvas.translate(0, -mShadowOffset);
        } else if (mLeftShow) {
            canvas.translate(mShadowOffset, 0);
        } else if (mRightShow) {
            canvas.translate(-mShadowOffset, 0);
        }
    }
}
