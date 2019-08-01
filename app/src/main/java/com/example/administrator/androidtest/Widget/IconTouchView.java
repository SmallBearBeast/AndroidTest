package com.example.administrator.androidtest.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.example.administrator.androidtest.R;

public class IconTouchView extends ImageView {
    private int mSrcColor;
    private int mDestColor;

    public IconTouchView(Context context) {
        this(context, null);
    }

    public IconTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IconTouchView);
        mSrcColor = array.getResourceId(R.styleable.IconTouchView_itv_src_icon_color, -1);
        mDestColor = array.getResourceId(R.styleable.IconTouchView_itv_dest_icon_color, -1);
        array.recycle();
        setEnabled(true);
        if(mSrcColor != -1){
            setColorFilter(mSrcColor);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                setColorFilter(mDestColor);
                break;

            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                setColorFilter(mSrcColor);
                break;
        }
        //默认返回 false 这样up事件无法被消费，手动改成true
        super.onTouchEvent(event);
        return true;
    }

    public void setSrcColor(int mSrcColor) {
        this.mSrcColor = mSrcColor;
        setColorFilter(mSrcColor);
    }

    public void setDestColor(int mDestColor) {
        this.mDestColor = mDestColor;
    }
}
