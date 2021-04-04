package com.example.libbase.Util;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;

public class Animator {
    public static final String ALPHA = "alpha";
    public static final String TRANSLATION_X = "translationX";
    public static final String TRANSLATION_Y = "translationY";
    public static final String SCALE_X = "scaleX";
    public static final String SCALE_Y = "scaleY";
    public static final String SCALE = "scale";
    public static final String ROTATION = "rotation";
    public static final String CUSTOMIZE = "customize";
    public static final int DEFAULT_DURATION = 300;
    private View mView;
    private int mDuration;
    private boolean mAutoCancel;
    private int mRepeatCount;
    private int mRepeatMode;
    private TimeInterpolator mInterpolator;
    private long mStartDelay;
    private float[] mFloatValues;
    private UpdateAdapter mUpdateAdapter;
    private PauseAdapter mPauseAdapter;
    private StatusAdapter mStatusAdapter;
    private String mProperty;

    public static Animator make(View view, String property, float[] values) {
        return make(view, property, DEFAULT_DURATION, values);
    }

    public static Animator make(View view, String property, int duration, float[] values) {
        Animator config = new Animator();
        config.mView = view;
        config.mDuration = duration;
        config.mFloatValues = values;
        config.mProperty = property;
        return config;
    }

    private void alpha() {
        if (mView == null || mFloatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mView, ALPHA, mFloatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    private void translationX() {
        if (mView == null || mFloatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mView, TRANSLATION_X, mFloatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    private void translationY() {
        if (mView == null || mFloatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mView, TRANSLATION_Y, mFloatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    private void scaleX() {
        if (mView == null || mFloatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mView, SCALE_X, mFloatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    private void scaleY() {
        if (mView == null || mFloatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mView, SCALE_Y, mFloatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    private void scale() {
        scaleX();
        scaleY();
    }

    private void rotation() {
        if (mView == null || mFloatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mView, ROTATION, mFloatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    private void customize() {
        if (mView == null || mFloatValues == null) {
            return;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mFloatValues);
        initConfig(valueAnimator);
        valueAnimator.start();
    }

    private void initConfig(ValueAnimator animator) {
        if (mDuration != 0) {
            animator.setDuration(mDuration);
        }
        if (animator instanceof ObjectAnimator) {
            ((ObjectAnimator) animator).setAutoCancel(mAutoCancel);
        }
        if (mRepeatCount > 0) {
            animator.setRepeatCount(mRepeatCount);
        }
        animator.setRepeatMode(mRepeatMode);
        if (mInterpolator != null) {
            animator.setInterpolator(mInterpolator);
        }
        if (mStartDelay > 0) {
            animator.setStartDelay(mStartDelay);
        }
        if (mUpdateAdapter != null) {
            animator.addUpdateListener(mUpdateAdapter);
        }
        if (mPauseAdapter != null) {
            animator.addPauseListener(mPauseAdapter);
        }
        if (mStatusAdapter != null) {
            animator.addListener(mStatusAdapter);
        }
    }

    public void start() {
        switch (mProperty) {
            case ALPHA:
                alpha();
                break;
            case ROTATION:
                rotation();
                break;
            case TRANSLATION_X:
                translationX();
                break;
            case TRANSLATION_Y:
                translationY();
                break;
            case SCALE_X:
                scaleX();
                break;
            case SCALE_Y:
                scaleY();
                break;
            case SCALE:
                scale();
                break;
            case CUSTOMIZE:
                customize();
                break;
        }
    }

    public Animator view(View view) {
        mView = view;
        return this;
    }

    public Animator duration(int duration) {
        mDuration = duration;
        return this;
    }

    public Animator autoCancel(boolean autoCancel) {
        mAutoCancel = autoCancel;
        return this;
    }

    public Animator repeatCount(int repeatCount) {
        mRepeatCount = repeatCount;
        return this;
    }

    public Animator repeatMode(int repeatMode) {
        mRepeatMode = repeatMode;
        return this;
    }

    public Animator interpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
        return this;
    }

    public Animator startDelay(long startDelay) {
        mStartDelay = startDelay;
        return this;
    }

    public Animator floatValues(float[] floatValues) {
        mFloatValues = floatValues;
        return this;
    }

    public Animator updateAdapter(UpdateAdapter updateAdapter) {
        mUpdateAdapter = updateAdapter;
        return this;
    }

    public Animator pauseAdapter(PauseAdapter pauseAdapter) {
        mPauseAdapter = pauseAdapter;
        return this;
    }

    public Animator statusAdapter(StatusAdapter statusAdapter) {
        mStatusAdapter = statusAdapter;
        return this;
    }

    public Animator property(String property) {
        mProperty = property;
        return this;
    }

    public static class UpdateAdapter implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {}
    }

    public static class PauseAdapter implements android.animation.Animator.AnimatorPauseListener {
        @Override
        public void onAnimationPause(android.animation.Animator animation) {}

        @Override
        public void onAnimationResume(android.animation.Animator animation) {}
    }

    public static class StatusAdapter extends AnimatorListenerAdapter {}
}
