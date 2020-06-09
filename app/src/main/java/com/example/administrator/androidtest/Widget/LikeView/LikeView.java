package com.example.administrator.androidtest.Widget.LikeView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;

public class LikeView extends FrameLayout {
    private boolean isLike;
    private boolean isAnimating;
    private int duration = 300;
    private int likeSrc = -1;
    private int unLikeSrc = -1;
    private int likeTintColor = Color.RED;
    private int unLikeTintColor =  Color.WHITE;
    private ImageView ivLike;
    private ImageView ivUnLike;

    private ValueAnimator likeZoomOutAnim;
    private ValueAnimator unLikeZoomOutAnim;
    private ValueAnimator unLikeZoomOffAnim;

    public LikeView(Context context) {
        this(context, null);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypeArray(context, attrs);
        initView(context);
        setLike(isLike);
    }

    private void initTypeArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LikeView);
        duration = typedArray.getInt(R.styleable.LikeView_lv_anim_duration, 300);
        isLike = typedArray.getBoolean(R.styleable.LikeView_lv_is_like, false);
        likeSrc = typedArray.getResourceId(R.styleable.LikeView_lv_like_src, -1);
        unLikeSrc = typedArray.getResourceId(R.styleable.LikeView_lv_un_like_src, -1);
        likeTintColor = typedArray.getColor(R.styleable.LikeView_lv_like_color, Color.RED);
        unLikeTintColor = typedArray.getColor(R.styleable.LikeView_lv_un_like_color, Color.WHITE);
        typedArray.recycle();
    }

    private void initView(Context context) {
        ivUnLike = new ImageView(context);
        ivUnLike.setImageResource(unLikeSrc);
        ivUnLike.setColorFilter(unLikeTintColor);
        ViewGroup.LayoutParams unLikeLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(ivUnLike, unLikeLp);

        ivLike = new ImageView(context);
        ivLike.setImageResource(likeSrc);
        ivLike.setColorFilter(likeTintColor);
        ViewGroup.LayoutParams likeLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(ivLike, likeLp);
    }

    public void like() {
        if (isAnimating) {
            return;
        }
        isLike = true;
        doLikeZoomOutAnim();
    }

    private void doLikeZoomOutAnim() {
        likeZoomOutAnim = ValueAnimator.ofFloat(0, 1f, 1.2f, 1f).setDuration(duration);
        likeZoomOutAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        likeZoomOutAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                ivLike.setScaleX(scale);
                ivLike.setScaleY(scale);
            }
        });
        likeZoomOutAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
                ivLike.setVisibility(VISIBLE);
                ivUnLike.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                likeZoomOutAnim.removeAllUpdateListeners();
                likeZoomOutAnim.removeAllListeners();
                setLike(true);
            }
        });
        likeZoomOutAnim.start();
    }

    public void unLike() {
        if (isAnimating) {
            return;
        }
        isLike = false;
        doUnLikeZoomOutAnim();
        doUnLikeZoomOffAnim();
    }

    private void doUnLikeZoomOutAnim() {
        unLikeZoomOutAnim = ValueAnimator.ofFloat(0, 1f, 1.2f, 1f).setDuration(duration);
        unLikeZoomOutAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        unLikeZoomOutAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                ivUnLike.setScaleX(scale);
                ivUnLike.setScaleY(scale);
            }
        });
        unLikeZoomOutAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
                ivLike.setVisibility(VISIBLE);
                ivUnLike.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                unLikeZoomOutAnim.removeAllUpdateListeners();
                unLikeZoomOutAnim.removeAllListeners();
                setLike(false);
            }
        });
        unLikeZoomOutAnim.start();
    }

    private void doUnLikeZoomOffAnim() {
        unLikeZoomOffAnim = ValueAnimator.ofFloat(1f, 0f).setDuration(duration);
        unLikeZoomOffAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        unLikeZoomOffAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                ivLike.setScaleX(scale);
                ivLike.setScaleY(scale);
            }
        });
        unLikeZoomOffAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
                ivLike.setVisibility(VISIBLE);
                ivUnLike.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                unLikeZoomOffAnim.removeAllUpdateListeners();
                unLikeZoomOffAnim.removeAllListeners();
                setLike(false);
            }
        });
        unLikeZoomOffAnim.start();
    }

    public void setLike(boolean like) {
        if (isAnimating) {
            isAnimating = false;
            if (likeZoomOutAnim != null && likeZoomOutAnim.isStarted()) {
                likeZoomOutAnim.cancel();
            }
            if (unLikeZoomOutAnim != null && unLikeZoomOutAnim.isStarted()) {
                unLikeZoomOutAnim.cancel();
            }
            if (unLikeZoomOffAnim != null && unLikeZoomOffAnim.isStarted()) {
                unLikeZoomOffAnim.cancel();
            }
        }
        isLike = like;
        ivLike.setScaleX(like ? 1f : 0f);
        ivLike.setScaleY(like ? 1f : 0f);
        ivLike.setVisibility(like ? VISIBLE : INVISIBLE);
        ivUnLike.setScaleX(like ? 0f : 1f);
        ivUnLike.setScaleY(like ? 0f : 1f);
        ivUnLike.setVisibility(like ? INVISIBLE : VISIBLE);
    }

    public boolean isLike() {
        return isLike;
    }
}
