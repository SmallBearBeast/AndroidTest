package com.example.administrator.androidtest.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.administrator.androidtest.R;

public class ShapeImageView extends AppCompatImageView {
    private static final int TYPE_RECTANGLE = 1;
    private static final int TYPE_CIRCLE = 2;
    private static final int TYPE_SQUARE = 3;
    private static final int TYPE_POLYGON = 4;
    private int mViewWidth = 0;
    private int mViewHeight = 0;
    private int mType;
    private int mRadius;
    private int mLeftTopRadius;
    private int mLeftBottomRadius;
    private int mRightTopRadius;
    private int mRightBottomRadius;
    private int mBorderSize;
    private int mBorderColor;
    private int mPolyGonSideNum;
    private Bitmap mBitmap;
    private Paint mShapePaint;
    private Paint mBorderPaint;
    private Path mShapePath;
    private Path mBorderPath;
    private RectF mSaveLayerRectF;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

    public ShapeImageView(Context context) {
        this(context, null);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeArray(context, attrs);
        initPaint();
        mShapePath = new Path();
        mBorderPath = new Path();
        mSaveLayerRectF = new RectF();
    }

    private void initTypeArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeImageView);
        mType = typedArray.getInteger(R.styleable.ShapeImageView_siv_type, TYPE_RECTANGLE);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.ShapeImageView_siv_radius, 0);
        mLeftTopRadius = typedArray.getDimensionPixelSize(R.styleable.ShapeImageView_siv_left_top_radius, 0);
        mLeftBottomRadius = typedArray.getDimensionPixelSize(R.styleable.ShapeImageView_siv_left_bottom_radius, 0);
        mRightTopRadius = typedArray.getDimensionPixelSize(R.styleable.ShapeImageView_siv_right_top_radius, 0);
        mRightBottomRadius = typedArray.getDimensionPixelSize(R.styleable.ShapeImageView_siv_right_bottom_radius, 0);
        mBorderSize = typedArray.getDimensionPixelSize(R.styleable.ShapeImageView_siv_border_width, 0);
        mBorderColor = typedArray.getColor(R.styleable.ShapeImageView_siv_border_color, Color.BLACK);
        mPolyGonSideNum = typedArray.getInteger(R.styleable.ShapeImageView_siv_polygon_side_num, 3);
        typedArray.recycle();
    }

    private void initPaint() {
        mShapePaint = new Paint();
        mShapePaint.setAntiAlias(true);
        mShapePaint.setDither(true);
        mShapePaint.setStyle(Paint.Style.FILL);
        mShapePaint.setColor(Color.WHITE);

        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mShapePaint.setDither(true);
        mBorderPaint.setStyle(Paint.Style.FILL);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mViewWidth = width;
        mViewHeight = height;
        if (mType == TYPE_CIRCLE || mType == TYPE_SQUARE || mType == TYPE_POLYGON) {
            if (width != height) {
                int size = Math.min(width, height);
                setMeasuredDimension(size, size);
                mViewWidth = mViewHeight = size;
            }
        }
        mSaveLayerRectF.set(mBorderSize, mBorderSize, mViewWidth - mBorderSize, mViewHeight - mBorderSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(mSaveLayerRectF, null, Canvas.ALL_SAVE_FLAG);
        // 缩放以后绘制的长度也同比例缩放
        scaleCanvas(canvas);
        super.onDraw(canvas);
        drawShape(canvas);
        canvas.restore();
        drawBorder(canvas);
    }

    private void drawShape(Canvas canvas) {
        mShapePath.reset();
        mBorderPath.reset();
        mShapePaint.setXfermode(mXfermode);
        if (isRectangle()) {
            drawRectangle(canvas);
        } else if (isCircle()) {
            drawCircle(canvas);
        } else if (isSquare()) {
            drawSquare(canvas);
        } else if (isPolygon()) {
            drawPolygon(canvas);
        }
        mShapePaint.setXfermode(null);
    }

    private void drawRectangle(Canvas canvas) {
        float[] radii = new float[8];
        boolean isPartSetRadius = false;
        if (mLeftTopRadius > 0) {
            radii[0] = radii[1] = mLeftTopRadius;
            isPartSetRadius = true;
        }
        if (mRightTopRadius > 0) {
            radii[2] = radii[3] = mRightTopRadius;
            isPartSetRadius = true;
        }
        if (mRightBottomRadius > 0) {
            radii[4] = radii[5] = mRightBottomRadius;
            isPartSetRadius = true;
        }
        if (mLeftBottomRadius > 0) {
            radii[6] = radii[7] = mLeftBottomRadius;
            isPartSetRadius = true;
        }
        if (!isPartSetRadius) {
            for (int i = 0; i < radii.length; i++) {
                radii[i] = mRadius;
            }
        }
        int width = mViewWidth;
        int height = mViewHeight;
        if (mBorderSize > 0) {
            mBorderPath.addRoundRect(new RectF(0, 0, mViewWidth, mViewHeight), radii, Path.Direction.CW);
            Path path = new Path();
            path.addRoundRect(new RectF(mBorderSize, mBorderSize, mViewWidth - mBorderSize, mViewHeight - mBorderSize), radii, Path.Direction.CW);
            mBorderPath.op(path, Path.Op.DIFFERENCE);
        }
        mShapePath.addRoundRect(new RectF(0, 0, width, height), radii, Path.Direction.CW);
        Path path = new Path();
        path.addRect(0, 0, width, height, Path.Direction.CW);
        if (path.op(mShapePath, Path.Op.DIFFERENCE)) {
            canvas.drawPath(path, mShapePaint);
        }
    }

    private void drawCircle(Canvas canvas) {
        int size = Math.min(mViewWidth, mViewHeight);
        float radius = size / 2.0f;
        if (mBorderSize > 0) {
            mBorderPath.addCircle(radius, radius, radius, Path.Direction.CW);
            Path path = new Path();
            path.addCircle(radius, radius, radius - mBorderSize, Path.Direction.CW);
            mBorderPath.op(path, Path.Op.DIFFERENCE);
        }
        mShapePath.addCircle(radius, radius, radius, Path.Direction.CW);
        Path path = new Path();
        path.addRect(0, 0, size, size, Path.Direction.CW);
        if (path.op(mShapePath, Path.Op.DIFFERENCE)) {
            canvas.drawPath(path, mShapePaint);
        }
    }

    private void drawSquare(Canvas canvas) {
        int size = Math.min(mViewWidth, mViewHeight);
        if (mBorderSize > 0) {
            mBorderPath.addRect(0, 0, size, size, Path.Direction.CW);
            Path path = new Path();
            path.addRect(mBorderSize, mBorderSize, size - mBorderSize, size - mBorderSize, Path.Direction.CW);
            mBorderPath.op(path, Path.Op.DIFFERENCE);
        }
        mShapePath.addRect(0, size, size, size, Path.Direction.CW);
        canvas.drawPath(mShapePath, mShapePaint);
    }

    private void drawPolygon(Canvas canvas) {
//        mShapePath.transform();
        int size = Math.min(mViewWidth, mViewHeight);
        if (mBorderSize > 0) {
//            mBorderPath.addPath(createPolygonPath(mPolyGonSideNum, size));
//            Path path = createPolygonPath(mPolyGonSideNum, size);
        }
        mShapePath.addPath(createPolygonPath(mPolyGonSideNum, size));
        Path path = new Path();
        path.addRect(0, 0, size, size, Path.Direction.CW);
        if (path.op(mShapePath, Path.Op.DIFFERENCE)) {
            canvas.drawPath(path, mShapePaint);
        }
    }

    private Path createPolygonPath(int polygonSideNum, int size) {
        Path path = new Path();
        float degree = 360f / polygonSideNum;
        for (int i = 0; i <= polygonSideNum; i++) {
            float x = (float) (size / 2f + Math.sin(i * degree) * size);
            float y = (float) (size / 2f + Math.cos(i * degree) * size);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        return path;
    }


    private void drawBorder(Canvas canvas) {
        if (mBorderSize > 0) {
            canvas.drawPath(mBorderPath, mBorderPaint);
        }
    }

    private void scaleCanvas(Canvas canvas) {
        if (mBorderSize <= 0) {
            return;
        }
        float sx = 1.0f * (mViewWidth - 2 * mBorderSize) / mViewWidth;
        float sy = 1.0f * (mViewHeight - 2 * mBorderSize) / mViewHeight;
        // 缩小画布，使图片内容不被borders覆盖
        canvas.scale(sx, sy, mViewWidth / 2.0f, mViewHeight / 2.0f);
    }

    private boolean isCircle() {
        return mType == TYPE_CIRCLE;
    }

    private boolean isRectangle() {
        return mType == TYPE_RECTANGLE;
    }

    private boolean isSquare() {
        return mType == TYPE_SQUARE;
    }

    private boolean isPolygon() {
        return mType == TYPE_POLYGON;
    }
}
