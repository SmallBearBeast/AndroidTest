package com.example.libbase.Com;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.AnimatorRes;
import androidx.annotation.LayoutRes;


public class ComToast {
    private static final int DEFAULT_DURATION = 3000;
    private int mLayoutId;
    private int mLeftMargin;
    private int mRightMargin;
    private int mTopMargin;
    private int mBottomMargin;
    private int mEnterAnimatorRes;
    private int mExitAnimatorRes;
    private int mDuration = DEFAULT_DURATION;
    private Activity mActivity;
    private View mContentView;
    private OnToastInitListener mOnToastInitListener;
    private Runnable mHideTask = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private ComToast(Activity activity, @LayoutRes int layoutId) {
        mActivity = activity;
        mLayoutId = layoutId;
    }

    public static ComToast make(Activity activity, @LayoutRes int layoutId) {
        return new ComToast(activity, layoutId);
    }

    public ComToast duration(int duration) {
        mDuration = duration;
        return this;
    }

    public ComToast enterAnimator(@AnimatorRes int enterAnimatorRes) {
        mEnterAnimatorRes = enterAnimatorRes;
        return this;
    }

    public ComToast exitAnimator(@AnimatorRes int exitAnimatorRes) {
        mExitAnimatorRes = exitAnimatorRes;
        return this;
    }

    public ComToast leftMargin(int leftMargin) {
        mLeftMargin = leftMargin;
        return this;
    }

    public ComToast rightMargin(int rightMargin) {
        mRightMargin = rightMargin;
        return this;
    }

    public ComToast topMargin(int topMargin) {
        mTopMargin = topMargin;
        return this;
    }

    public ComToast bottomMargin(int bottomMargin) {
        mBottomMargin = bottomMargin;
        return this;
    }

    public ComToast toastInitListener(OnToastInitListener listener) {
        mOnToastInitListener = listener;
        return this;
    }

    public void show() {
        View contentView = mActivity.findViewById(android.R.id.content);
        if (contentView instanceof FrameLayout) {
            FrameLayout contentViewGroup = (FrameLayout) contentView;
            mContentView = LayoutInflater.from(mActivity).inflate(mLayoutId, contentViewGroup, false);
            if (mOnToastInitListener != null) {
                mOnToastInitListener.onInit(mContentView);
            }
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentView.getLayoutParams();
            lp.setMargins(mLeftMargin, mTopMargin, mRightMargin, mBottomMargin);
            contentViewGroup.addView(mContentView, lp);
            Animator animator = AnimatorInflater.loadAnimator(mActivity, mEnterAnimatorRes);
            animator.start();
            new Handler().postDelayed(mHideTask, mDuration);
        }
    }

    private void hide() {
        if (mContentView != null && mContentView.getParent() != null) {
            final View contentView = mActivity.findViewById(android.R.id.content);
            if (contentView instanceof FrameLayout) {
                Animator animator = AnimatorInflater.loadAnimator(mActivity, mExitAnimatorRes);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        FrameLayout contentViewGroup = (FrameLayout) contentView;
                        contentViewGroup.removeView(mContentView);
                        mContentView = null;
                    }
                });
                animator.start();
            }
            mContentView = null;
        }
    }

    public interface OnToastInitListener {
        void onInit(View view);
    }
}
