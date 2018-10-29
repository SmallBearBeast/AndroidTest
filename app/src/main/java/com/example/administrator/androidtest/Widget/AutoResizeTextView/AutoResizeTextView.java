package com.example.administrator.androidtest.Widget.AutoResizeTextView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * Created on 2017/11/9
 * Description:
 * 根据TextView控件宽度动态缩小其中的文本，以适配多语言的长度溢出的问题
 * 注：需要和TextView的android:maxWidth属性配合使用，且只适用于单行文本的情形
 *
 * @author zhanglinwei(G7901)
 */
public class AutoResizeTextView extends AppCompatTextView {
    public AutoResizeTextView(Context context) {
        super(context);
        init();
    }

    public AutoResizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoResizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMaxLines(1);
        int gravity = Gravity.CENTER_VERTICAL | getGravity();
        setGravity(gravity);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        adjustTextSize();
    }

    public void adjustTextSize(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && 1 < getMaxLines()){
            adjustTextSizeMultiLines();
        } else {
            adaptationFollowTextSize();
        }
    }
    private void adaptationFollowTextSize() {
        int width = getMeasuredWidth();

        String text = getText().toString();
        int avaiWidth = width - getPaddingLeft() - getPaddingRight();
        avaiWidth -= getCompoundDrawablePadding();
        Drawable[] drawables = getCompoundDrawables();
        if (drawables.length > 0 && drawables[0] != null) {
            avaiWidth -= drawables[0].getIntrinsicWidth();
        }
        if (drawables.length > 2 && drawables[2] != null) {
            avaiWidth -= drawables[2].getIntrinsicWidth();
        }
        if (avaiWidth <= 0) {
            return;
        }
        TextPaint textPaintClone = new TextPaint(getPaint());
        // note that Paint text size works in px not sp
        float trySize = textPaintClone.getTextSize();

        while (textPaintClone.measureText(text) > avaiWidth) {
            trySize--;
            textPaintClone.setTextSize(trySize);
        }
        setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
    }

    private void adjustTextSizeMultiLines() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Log.d("AutoResizeTextView", "adjustTextSizeMultiLines maxLines=" + getMaxLines());
        } else {
            Log.d("AutoResizeTextView", "adjustTextSizeMultiLines maxLines=invalid");
        }

        int width = getMeasuredWidth();
        String text = getText().toString();
        int avaiWidth = width - getPaddingLeft() - getPaddingRight();
        avaiWidth -= getCompoundDrawablePadding();
        Drawable[] drawables = getCompoundDrawables();
        if (drawables.length > 0 && drawables[0] != null) {
            avaiWidth -= drawables[0].getIntrinsicWidth();
        }
        if (drawables.length > 2 && drawables[2] != null) {
            avaiWidth -= drawables[2].getIntrinsicWidth();
        }
        Log.d("AutoResizeTextview", "avaiWidth="+avaiWidth);
        if (avaiWidth <= 0) {
            return;
        }
        TextPaint textPaintClone = new TextPaint(getPaint());
        // note that Paint text size works in px not sp
        float trySize = textPaintClone.getTextSize();
        int maxLines = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            maxLines = getMaxLines();
        }

        while (needShrink(text, textPaintClone, avaiWidth, maxLines) && trySize >0) {
            Log.d("AutoResizeTextview","trySize ="+trySize);
            trySize--;
            textPaintClone.setTextSize(trySize);
        }
        setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
    }

    private boolean needShrink(String text, TextPaint textPaintClone, int maxWidthEachLine, final int maxLines){
        StaticLayout layout = new StaticLayout(text, getPaint(), maxWidthEachLine, Layout.Alignment.ALIGN_NORMAL, 1.0f,
                0.0f, true);
        Log.d("AutoResizeTextview","layout.getLineCount ="+layout.getLineCount() +", maxline="+maxLines);
        if(layout.getLineCount() > maxLines){
            return true;
        }else {
            return false;
        }
    }
}
