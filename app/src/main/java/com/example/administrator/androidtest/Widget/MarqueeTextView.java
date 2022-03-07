package com.example.administrator.androidtest.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import com.example.administrator.androidtest.R;

public class MarqueeTextView extends androidx.appcompat.widget.AppCompatTextView {

    private boolean marqueeEnable;
    private int delayMillis;
    private final Runnable delayMarqueeTask = () -> enableMarquee(true);

    public MarqueeTextView(Context context) {
        this(context, null);
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeTextView);
        marqueeEnable = canMarquee() && typedArray.getBoolean(R.styleable.MarqueeTextView_mtv_marquee_enable, true);
        delayMillis = typedArray.getInt(R.styleable.MarqueeTextView_mtv_delayMillis, 0);
        typedArray.recycle();
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        if (marqueeEnable) {
            startMarquee(delayMillis);
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) {
            super.onFocusChanged(marqueeEnable, direction, previouslyFocusedRect);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus) {
            super.onWindowFocusChanged(marqueeEnable);
        }
    }

    @Override
    public boolean isFocused() {
        return marqueeEnable;
    }

    public void startMarquee() {
        startMarquee(0L);
    }

    public void startMarquee(long delayMillis) {
        if (!canMarquee()) {
            return;
        }
        if (delayMillis <= 0L && Looper.myLooper() == Looper.getMainLooper()) {
            delayMarqueeTask.run();
        } else {
            enableMarquee(false);
            postDelayed(delayMarqueeTask, delayMillis);
        }
    }

    public void endMarquee() {
        removeCallbacks(delayMarqueeTask);
        enableMarquee(false);
    }

    private void enableMarquee(boolean enable) {
        marqueeEnable = enable;
        setFocusable(enable);
        setFocusableInTouchMode(enable);
        super.onWindowFocusChanged(enable);
    }

    public void recover() {
        if (marqueeEnable) {
            startMarquee(delayMillis);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(delayMarqueeTask);
        super.onDetachedFromWindow();
    }

    private boolean canMarquee() {
        return getVisibility() == View.VISIBLE && getText().length() > 0;
    }

    private static final String TAG = "MarqueeTextView";
}
