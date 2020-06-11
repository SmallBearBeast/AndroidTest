package com.example.libbase.Util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;

public class AnimatorConfig {
    public static final String ALPHA = "alpha";
    public static final String TRANSLATION_X = "translationX";
    public static final String TRANSLATION_Y = "translationY";
    public static final String SCALE_X = "scaleX";
    public static final String SCALE_Y = "scaleY";
    public static final String SCALE = "scale";
    public static final String ROTATION = "rotation";
    public View view;
    public int duration;
    public boolean autoCancel;
    public int repeatCount;
    public int repeatMode;
    public TimeInterpolator interpolator;
    public long startDelay;
    public float[] floatValues;
    public int[] intValues;
    public UpdateAdapter updateAdapter;
    public PauseAdapter pauseAdapter;
    public StatusAdapter statusAdapter;
    public String property;

    public static AnimatorConfig make(View view, String property, int duration, float[] values) {
        AnimatorConfig config = new AnimatorConfig();
        config.view = view;
        config.duration = duration;
        config.floatValues = values;
        config.property = property;
        return config;
    }

    public static AnimatorConfig make(View view, String property, int duration, int[] values) {
        AnimatorConfig config = new AnimatorConfig();
        config.view = view;
        config.duration = duration;
        config.intValues = values;
        config.property = property;
        return config;
    }

    public void alpha() {
        if (view == null || floatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", floatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    public void translationX() {
        if (view == null || floatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", floatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    public void translationY() {
        if (view == null || floatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", floatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    public void scaleX() {
        if (view == null || floatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "scaleX", floatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    public void scaleY() {
        if (view == null || floatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "scaleY", floatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    public void scale() {
        scaleX();
        scaleY();
    }

    public void rotation() {
        if (view == null || floatValues == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", floatValues);
        initConfig(objectAnimator);
        objectAnimator.start();
    }

    private void initConfig(ObjectAnimator objectAnimator) {
        if (duration != 0) {
            objectAnimator.setDuration(duration);
        }
        objectAnimator.setAutoCancel(autoCancel);
        if (repeatCount > 0) {
            objectAnimator.setRepeatCount(repeatCount);
        }
        objectAnimator.setRepeatMode(repeatMode);
        if (interpolator != null) {
            objectAnimator.setInterpolator(interpolator);
        }
        if (startDelay > 0) {
            objectAnimator.setStartDelay(startDelay);
        }
        if (updateAdapter != null) {
            objectAnimator.addUpdateListener(updateAdapter);
        }
        if (pauseAdapter != null) {
            objectAnimator.addPauseListener(pauseAdapter);
        }
        if (statusAdapter != null) {
            objectAnimator.addListener(statusAdapter);
        }
    }

    public void start() {
        switch (property) {
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
        }
    }

    public static class UpdateAdapter implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {

        }
    }

    public static class PauseAdapter implements Animator.AnimatorPauseListener {
        @Override
        public void onAnimationPause(Animator animation) {

        }

        @Override
        public void onAnimationResume(Animator animation) {

        }
    }

    public static class StatusAdapter extends AnimatorListenerAdapter {

    }
}
