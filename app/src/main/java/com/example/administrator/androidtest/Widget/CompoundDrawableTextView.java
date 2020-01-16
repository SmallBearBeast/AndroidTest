package com.example.administrator.androidtest.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.administrator.androidtest.R;

// TODO: 2018/12/21 textview 周围drawable点击控件 
public class CompoundDrawableTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final int DRAWABLE_NONE = -1;
    private static final int DRAWABLE_COUNT = 4;
    public static final int DRAWABLE_LEFT = 1;
    public static final int DRAWABLE_TOP = DRAWABLE_LEFT + 1;
    public static final int DRAWABLE_RIGHT = DRAWABLE_TOP + 1;
    public static final int DRAWABLE_BOTTOM = DRAWABLE_RIGHT + 1;
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private int mTouchX;
    private int mTouchY;
    private StatusDrawable mLeftStatusDrawable;
    private StatusDrawable mRightStatusDrawable;
    private StatusDrawable mTopStatusDrawable;
    private StatusDrawable mBottomStatusDrawable;
    private ClickWrapper mClickWrapper;

    public CompoundDrawableTextView(Context context) {
        this(context, null);
    }

    public CompoundDrawableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        Drawable[] drawables = getCompoundDrawables();
        Drawable[] clickDrawables = new Drawable[DRAWABLE_COUNT];
        Drawable[] pressDrawables = new Drawable[DRAWABLE_COUNT];
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.CompoundDrawableTextView);

        int drawableId = array.getResourceId(R.styleable.CompoundDrawableTextView_cdtv_click_left_drawableId, DRAWABLE_NONE);
        if(drawableId != DRAWABLE_NONE)
            clickDrawables[0] = ContextCompat.getDrawable(context, drawableId);

        drawableId = array.getResourceId(R.styleable.CompoundDrawableTextView_cdtv_click_top_drawableId, DRAWABLE_NONE);
        if(drawableId != DRAWABLE_NONE)
            clickDrawables[1] = ContextCompat.getDrawable(context, drawableId);

        drawableId = array.getResourceId(R.styleable.CompoundDrawableTextView_cdtv_click_right_drawableId, DRAWABLE_NONE);
        if(drawableId != DRAWABLE_NONE)
            clickDrawables[2] = ContextCompat.getDrawable(context, drawableId);

        drawableId = array.getResourceId(R.styleable.CompoundDrawableTextView_cdtv_click_bottom_drawableId, DRAWABLE_NONE);
        if(drawableId != DRAWABLE_NONE)
            clickDrawables[3] = ContextCompat.getDrawable(context, drawableId);

        drawableId = array.getResourceId(R.styleable.CompoundDrawableTextView_cdtv_press_left_drawableId, DRAWABLE_NONE);
        if(drawableId != DRAWABLE_NONE)
            pressDrawables[0] = ContextCompat.getDrawable(context, drawableId);

        drawableId = array.getResourceId(R.styleable.CompoundDrawableTextView_cdtv_press_top_drawableId, DRAWABLE_NONE);
        if(drawableId != DRAWABLE_NONE)
            pressDrawables[1] = ContextCompat.getDrawable(context, drawableId);

        drawableId = array.getResourceId(R.styleable.CompoundDrawableTextView_cdtv_press_right_drawableId, DRAWABLE_NONE);
        if(drawableId != DRAWABLE_NONE)
            pressDrawables[2] = ContextCompat.getDrawable(context, drawableId);

        drawableId = array.getResourceId(R.styleable.CompoundDrawableTextView_cdtv_press_bottom_drawableId, DRAWABLE_NONE);
        if(drawableId != DRAWABLE_NONE)
            pressDrawables[3] = ContextCompat.getDrawable(context, drawableId);

        initStatusDrawables(drawables, clickDrawables, pressDrawables);
        array.recycle();
    }

    private void initStatusDrawables(Drawable[] drawables, Drawable[] clickDrawables, Drawable[] pressDrawables) {
        StatusDrawable[] statusDrawables = new StatusDrawable[DRAWABLE_COUNT];
        for (int i = 0; i < DRAWABLE_COUNT; i++) {
            StatusDrawable statusDrawable = new StatusDrawable();
            statusDrawable.mCurDrawable = drawables[i];
            statusDrawable.mDrawable = drawables[i];
            statusDrawable.mClickDrawable = (clickDrawables[i] != null ? clickDrawables[i] : createEmptyDrawable(drawables[i]));
            statusDrawable.mPressDrawable = pressDrawables[i];
            statusDrawables[i] = statusDrawable;
        }
        mLeftStatusDrawable = statusDrawables[0];
        mTopStatusDrawable = statusDrawables[1];
        mRightStatusDrawable = statusDrawables[2];
        mBottomStatusDrawable = statusDrawables[3];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(mWidth == 0 && mWidth != w){
            mWidth = w;
        }
        if(mHeight == 0 && mHeight != h){
            mHeight = h;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mTouchX = (int) event.getX();
                mTouchY = (int) event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                mTouchX = (int) event.getX();
                mTouchY = (int) event.getY();
                if(mLeftStatusDrawable.isNormal()) {
                    if (isCanLeftClick(mLeftStatusDrawable.mPressDrawable)) {
                        setPressStatusDrawable(DRAWABLE_LEFT);
                    } else {
                        resetStatusDrawable(mLeftStatusDrawable);
                    }
                }
                if(mTopStatusDrawable.isNormal()){
                    if(isCanTopClick(mTopStatusDrawable.mPressDrawable)){
                        setPressStatusDrawable(DRAWABLE_TOP);
                    }else {
                        resetStatusDrawable(mTopStatusDrawable);
                    }
                }
                if(mRightStatusDrawable.isNormal()){
                    if(isCanRightClick(mRightStatusDrawable.mPressDrawable)){
                        setPressStatusDrawable(DRAWABLE_RIGHT);
                    }else {
                        resetStatusDrawable(mRightStatusDrawable);
                    }
                }
                if(mBottomStatusDrawable.isNormal()){
                    if(isCanBottomClick(mBottomStatusDrawable.mPressDrawable)){
                        setPressStatusDrawable(DRAWABLE_BOTTOM);
                    }else {
                        resetStatusDrawable(mBottomStatusDrawable);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mClickWrapper != null){
                    if(!mLeftStatusDrawable.isClick() && isCanLeftClick(mLeftStatusDrawable.mDrawable)){
                        mClickWrapper.onLeftClick();
                        setClickStatusDrawable(DRAWABLE_LEFT);
                    }else if(!mTopStatusDrawable.isClick() && isCanTopClick(mTopStatusDrawable.mDrawable)){
                        mClickWrapper.onTopClick();
                        setClickStatusDrawable(DRAWABLE_TOP);
                    }else if(!mRightStatusDrawable.isClick() && isCanRightClick(mRightStatusDrawable.mDrawable)){
                        mClickWrapper.onRightClick();
                        setClickStatusDrawable(DRAWABLE_RIGHT);
                    }else if(!mBottomStatusDrawable.isClick() && isCanBottomClick(mBottomStatusDrawable.mDrawable)){
                        mClickWrapper.onBottomClick();
                        setClickStatusDrawable(DRAWABLE_BOTTOM);
                    }else {
                        mClickWrapper.onClick();
                    }
                }
                break;
        }
        super.onTouchEvent(event);
        return true;
    }

    private boolean isCanBottomClick(Drawable drawable) {
        if(drawable == null)
            return false;
        Rect rect = new Rect();
        rect.left = (mWidth - drawable.getIntrinsicWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
        rect.right = (mWidth - drawable.getIntrinsicWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft() + drawable.getIntrinsicWidth();
        rect.top = mHeight - getPaddingBottom() - drawable.getIntrinsicHeight();
        rect.bottom = mHeight - getPaddingBottom();
        return rect.contains(mTouchX, mTouchY);
    }

    private boolean isCanTopClick(Drawable drawable) {
        if(drawable == null)
            return false;
        Rect rect = new Rect();
        rect.left = (mWidth - drawable.getIntrinsicWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
        rect.right = (mWidth - drawable.getIntrinsicWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft() + drawable.getIntrinsicWidth();
        rect.top = getPaddingTop();
        rect.bottom = getPaddingTop() + drawable.getIntrinsicHeight();
        return rect.contains(mTouchX, mTouchY);
    }

    private boolean isCanRightClick(Drawable drawable) {
        if(drawable == null)
            return false;
        Rect rect = new Rect();
        rect.left = mWidth - getPaddingRight() - drawable.getIntrinsicWidth();
        rect.right = mWidth - getPaddingRight();
        rect.top = (mHeight - drawable.getIntrinsicHeight() - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop();
        rect.bottom = (mHeight - drawable.getIntrinsicHeight() - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop() + drawable.getIntrinsicHeight();
        return rect.contains(mTouchX, mTouchY);
    }

    private boolean isCanLeftClick(Drawable drawable) {
        if(drawable == null)
            return false;
        Rect rect = new Rect();
        rect.left = getPaddingLeft();
        rect.right = drawable.getIntrinsicWidth() + getPaddingLeft();
        rect.top = (mHeight - drawable.getIntrinsicHeight() - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop();
        rect.bottom = (mHeight - drawable.getIntrinsicHeight() - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop() + drawable.getIntrinsicHeight();
        return rect.contains(mTouchX, mTouchY);
    }


    public void setClickWrapper(ClickWrapper clickWrapper){
        mClickWrapper = clickWrapper;
    }

    /**
     * 生成空白相同大小的空白drawable
     */
    private Drawable createEmptyDrawable(Drawable drawable){
        if(drawable != null){
            ColorDrawable d = new ColorDrawable();
            d.setBounds(drawable.getBounds());
            return d;
        }
        return null;
    }

    private void resetStatusDrawable(StatusDrawable statusDrawable){
        statusDrawable.setStatus(StatusDrawable.STATE_NORMAL);
        setCompoundDrawables(statusDrawable.mDrawable, statusDrawable.mDrawable, statusDrawable.mDrawable, statusDrawable.mDrawable);
    }

    private void setClickStatusDrawable(int drawableType){
        switch (drawableType){
            case DRAWABLE_LEFT:
                mLeftStatusDrawable.setStatus(StatusDrawable.STATE_CLICK);
                mLeftStatusDrawable.mCurDrawable = mLeftStatusDrawable.mClickDrawable;
                setCompoundDrawables(mLeftStatusDrawable.mCurDrawable, mTopStatusDrawable.mCurDrawable, mRightStatusDrawable.mCurDrawable, mBottomStatusDrawable.mCurDrawable);
                break;

            case DRAWABLE_TOP:
                mTopStatusDrawable.setStatus(StatusDrawable.STATE_CLICK);
                mTopStatusDrawable.mCurDrawable = mTopStatusDrawable.mClickDrawable;
                setCompoundDrawables(mLeftStatusDrawable.mCurDrawable, mTopStatusDrawable.mCurDrawable, mRightStatusDrawable.mCurDrawable, mBottomStatusDrawable.mCurDrawable);
                break;

            case DRAWABLE_RIGHT:
                mRightStatusDrawable.setStatus(StatusDrawable.STATE_CLICK);
                mRightStatusDrawable.mCurDrawable = mRightStatusDrawable.mClickDrawable;
                setCompoundDrawables(mLeftStatusDrawable.mCurDrawable, mTopStatusDrawable.mCurDrawable, mRightStatusDrawable.mCurDrawable, mBottomStatusDrawable.mCurDrawable);
                break;

            case DRAWABLE_BOTTOM:
                mBottomStatusDrawable.setStatus(StatusDrawable.STATE_CLICK);
                mBottomStatusDrawable.mCurDrawable = mBottomStatusDrawable.mClickDrawable;
                setCompoundDrawables(mLeftStatusDrawable.mCurDrawable, mTopStatusDrawable.mCurDrawable, mRightStatusDrawable.mCurDrawable, mBottomStatusDrawable.mCurDrawable);
                break;
        }

    }

    private void setPressStatusDrawable(int drawableType){
        switch (drawableType){
            case DRAWABLE_LEFT:
                mLeftStatusDrawable.setStatus(StatusDrawable.STATE_PRESS);
                mLeftStatusDrawable.mCurDrawable = mLeftStatusDrawable.mPressDrawable;
                setCompoundDrawables(mLeftStatusDrawable.mCurDrawable, mTopStatusDrawable.mCurDrawable, mRightStatusDrawable.mCurDrawable, mBottomStatusDrawable.mCurDrawable);
                break;

            case DRAWABLE_TOP:
                mTopStatusDrawable.setStatus(StatusDrawable.STATE_PRESS);
                mTopStatusDrawable.mCurDrawable = mTopStatusDrawable.mPressDrawable;
                setCompoundDrawables(mLeftStatusDrawable.mCurDrawable, mTopStatusDrawable.mCurDrawable, mRightStatusDrawable.mCurDrawable, mBottomStatusDrawable.mCurDrawable);
                break;

            case DRAWABLE_RIGHT:
                mRightStatusDrawable.setStatus(StatusDrawable.STATE_PRESS);
                mRightStatusDrawable.mCurDrawable = mRightStatusDrawable.mPressDrawable;
                setCompoundDrawables(mLeftStatusDrawable.mCurDrawable, mTopStatusDrawable.mCurDrawable, mRightStatusDrawable.mCurDrawable, mBottomStatusDrawable.mCurDrawable);
                break;

            case DRAWABLE_BOTTOM:
                mBottomStatusDrawable.setStatus(StatusDrawable.STATE_PRESS);
                mBottomStatusDrawable.mCurDrawable = mBottomStatusDrawable.mPressDrawable;
                setCompoundDrawables(mLeftStatusDrawable.mCurDrawable, mTopStatusDrawable.mCurDrawable, mRightStatusDrawable.mCurDrawable, mBottomStatusDrawable.mCurDrawable);
                break;
        }

    }


    public void setClickDrawable(Drawable drawable, int drawableType){
        switch (drawableType){
            case DRAWABLE_LEFT:
                mLeftStatusDrawable.mClickDrawable = drawable;
                if(mLeftStatusDrawable.isClick())
                    setCompoundDrawables(drawable, mTopStatusDrawable.mDrawable, mRightStatusDrawable.mDrawable, mBottomStatusDrawable.mDrawable);
                break;

            case DRAWABLE_TOP:
                mTopStatusDrawable.mClickDrawable = drawable;
                if(mTopStatusDrawable.isClick())
                    setCompoundDrawables(mLeftStatusDrawable.mDrawable, drawable, mRightStatusDrawable.mDrawable, mBottomStatusDrawable.mDrawable);
                break;

            case DRAWABLE_RIGHT:
                mRightStatusDrawable.mClickDrawable = drawable;
                if(mRightStatusDrawable.isClick())
                    setCompoundDrawables(mLeftStatusDrawable.mDrawable, mTopStatusDrawable.mDrawable, drawable, mBottomStatusDrawable.mDrawable);
                break;

            case DRAWABLE_BOTTOM:
                mBottomStatusDrawable.mClickDrawable = drawable;
                if(mBottomStatusDrawable.isClick())
                    setCompoundDrawables(mLeftStatusDrawable.mDrawable, mTopStatusDrawable.mDrawable, mRightStatusDrawable.mDrawable, drawable);
                break;
        }
    }

    public void setPressDrawable(Drawable drawable, int drawableType){
        switch (drawableType){
            case DRAWABLE_LEFT:
                mLeftStatusDrawable.mPressDrawable = drawable;
                break;

            case DRAWABLE_TOP:
                mTopStatusDrawable.mPressDrawable = drawable;
                break;

            case DRAWABLE_RIGHT:
                mRightStatusDrawable.mPressDrawable = drawable;
                break;

            case DRAWABLE_BOTTOM:
                mBottomStatusDrawable.mPressDrawable = drawable;
                break;
        }
    }


    public static class ClickWrapper{
        protected void onLeftClick(){};
        protected void onRightClick(){};
        protected void onTopClick(){};
        protected void onBottomClick(){};
        protected void onClick(){};
    }


    static class StatusDrawable{
        static final int STATE_NORMAL = 1;
        static final int STATE_PRESS = STATE_NORMAL + 1;
        static final int STATE_CLICK = STATE_PRESS + 1;
        Drawable mCurDrawable;
        Drawable mDrawable;
        Drawable mClickDrawable;
        Drawable mPressDrawable;
        int mStatus;

        public boolean isNormal(){
            return mStatus == STATE_NORMAL;
        }

        public boolean isPress(){
            return mStatus == STATE_PRESS;
        }

        public boolean isClick(){
            return mStatus == STATE_CLICK;
        }

        public void setStatus(int status){
            mStatus = status;
        }
    }
}
