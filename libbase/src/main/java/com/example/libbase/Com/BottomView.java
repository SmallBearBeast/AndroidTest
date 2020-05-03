package com.example.libbase.Com;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.ViewCompat;

public class BottomView {
    private static final String NAVIGATION_BAR = "navigationBarBackground";
    private static final String STATUS_BAR = "navigationBarBackground";
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private boolean mIsCreated;
    private boolean mIsDoingAnim;
    private boolean mIsShowed;
    private boolean mDimEnable;
    private boolean mDraggable = true;
    private boolean mMaskable = true;
    private boolean mMaskCancelable = true;
    private float mDimAmount = 0.5f;
    private int mBottomOffset;
    private int mAnimDuration = 200;
    private Activity mActivity;
    private View mMaskView;
    private View mContentView;
    private FrameLayout mParentView;
    private Handler mMainHandler = new Handler();

    public BottomView(Activity activity) {
        mActivity = activity;
    }

    public BottomView contentView(@LayoutRes int layoutId) {
        FrameLayout decorViewGroup = (FrameLayout) getDecorView();
        mContentView = LayoutInflater.from(mActivity).inflate(layoutId, decorViewGroup, false);
        return this;
    }

    public BottomView contentView(View contentView) {
        mContentView = contentView;
        return this;
    }

    public BottomView bottomOffset(int bottomOffset) {
        mBottomOffset = bottomOffset;
        return this;
    }

    public BottomView dimEnable(boolean dimEnable) {
        mDimEnable = dimEnable;
        return this;
    }

    public BottomView dimAmount(float dimAmount) {
        mDimAmount = dimAmount;
        return this;
    }

    public BottomView animDuration(int animDuration) {
        mAnimDuration = animDuration;
        return this;
    }

    public BottomView dragEnable(boolean dragEnable) {
        mDraggable = dragEnable;
        return this;
    }

    public BottomView maskEnable(boolean maskEnable) {
        mMaskable = maskEnable;
        return this;
    }

    public BottomView maskCancelable(boolean cancelable) {
        mMaskCancelable = cancelable;
        return this;
    }

    public BottomView hideViews(View... views) {
        for (View view : views) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomView.this.hide();
                }
            });
        }
        return this;
    }

    public void show() {
        if (!checkActivity()) {
            return;
        }
        if (mContentView == null) {
            return;
        }
        if (mIsShowed) {
            return;
        }
        mIsShowed = true;
        if (!mIsCreated) {
            mIsCreated = true;
            initView();
            initTouch();
        }
        doShowAnim();
    }

    private void initView() {
        mParentView = new InternalView(mActivity);
        mMaskView = new View(mActivity);
        mMaskView.setBackgroundColor(Color.BLACK);
        mMaskView.setAlpha(0);
        FrameLayout.LayoutParams maskViewLp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParentView.addView(mMaskView, maskViewLp);
        FrameLayout.LayoutParams contentViewLp = (FrameLayout.LayoutParams) mContentView.getLayoutParams();
        if (contentViewLp == null) {
            contentViewLp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            contentViewLp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        contentViewLp.gravity = Gravity.BOTTOM;
        mParentView.addView(mContentView, contentViewLp);

        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (isNavigationBarExist()) {
            mBottomOffset = mBottomOffset + getNavigationBarHeight();
        }
        flp.setMargins(0, 0, 0, mBottomOffset);
        mParentView.setLayoutParams(flp);
    }

    private void initTouch() {
        if (mMaskable) {
            if (mMaskCancelable) {
                mMaskView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BottomView.this.hide();
                    }
                });
            } else {
                mMaskView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        }
        if (mDraggable) {
            mContentView.setOnTouchListener(new InternalTouchListener(mActivity));
        }
    }

    public void hide() {
        if (!mIsShowed) {
            return;
        }
        mIsShowed = false;
        doHideAnim();
    }

    private void doResetAnim() {
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(mContentView, "translationY", 0);
        translationYAnim.setDuration(mAnimDuration);
        translationYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsDoingAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsDoingAnim = false;
                animation.removeListener(this);
            }
        });
        translationYAnim.start();
    }

    private void doShowAnim() {
        addToDecorView();
        mParentView.setVisibility(View.INVISIBLE);
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(mContentView, "translationY", mContentView.getHeight(), 0);
                translationYAnim.setDuration(mAnimDuration);
                translationYAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mIsDoingAnim = true;
                        mParentView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mIsDoingAnim = false;
                        animation.removeListener(this);
                    }
                });
                translationYAnim.start();
                if (mDimEnable) {
                    ObjectAnimator dimAnim = ObjectAnimator.ofFloat(mMaskView, "alpha", 0, mDimAmount);
                    dimAnim.setDuration(mAnimDuration);
                    dimAnim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animation.removeListener(this);
                        }
                    });
                    dimAnim.start();
                }

            }
        });
    }

    private void doHideAnim() {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(mContentView, "translationY", mContentView.getHeight());
                translationYAnim.setDuration(mAnimDuration);
                translationYAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mIsDoingAnim = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mIsDoingAnim = false;
                        animation.removeListener(this);
                        removeFromDecorView();
                    }
                });
                translationYAnim.start();
                if (mDimEnable) {
                    ObjectAnimator dimAnim = ObjectAnimator.ofFloat(mMaskView, "alpha", mDimAmount, 0);
                    dimAnim.setDuration(mAnimDuration);
                    dimAnim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animation.removeListener(this);
                        }
                    });
                    dimAnim.start();
                }
            }
        });
    }

    private boolean checkActivity() {
        return mActivity != null && !mActivity.isFinishing();
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return mContentView.findViewById(id);
    }

    protected Activity getActivity() {
        return mActivity;
    }

    protected View getContentView() {
        return mContentView;
    }

    public boolean isShow() {
        return mIsShowed;
    }

    private void checkUpAnim() {
        if (mContentView.getTranslationY() >= mContentView.getHeight() / 2.0f) {
            hide();
        } else {
            doResetAnim();
        }
    }

    private int getNavigationBarHeight() {
        int navigationBarHeight = -1;
        int resourceId = mActivity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if(resourceId > 0){
            navigationBarHeight = mActivity.getResources().getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    private boolean isStatusBarExist(){
        ViewGroup vp = (ViewGroup) getDecorView();
        for (int i = 0; i < vp.getChildCount(); i++) {
            vp.getChildAt(i).getContext().getPackageName();
            if (vp.getChildAt(i).getId()!= View.NO_ID && STATUS_BAR.equals(mActivity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                return true;
            }
        }
        return false;
    }

    public int getStatusBarHeight(){
        int statusBarHeight = -1;
        int resourceId = mActivity.getResources().getIdentifier("status_bar_height","dimen", "android");
        if(resourceId > 0){
            statusBarHeight = mActivity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    private boolean isNavigationBarExist(){
        ViewGroup vp = (ViewGroup) getDecorView();
        for (int i = 0; i < vp.getChildCount(); i++) {
            vp.getChildAt(i).getContext().getPackageName();
            if (vp.getChildAt(i).getId()!= View.NO_ID && NAVIGATION_BAR.equals(mActivity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                return true;
            }
        }
        return false;
    }

    private View getDecorView() {
        return mActivity.getWindow().getDecorView();
    }

    private void addToDecorView() {
        ViewGroup viewGroup = (ViewGroup) getDecorView();
        viewGroup.addView(mParentView);
    }

    private void removeFromDecorView() {
        ViewGroup viewGroup = (ViewGroup) getDecorView();
        viewGroup.removeView(mParentView);
    }

    private class InternalView extends FrameLayout implements NestedScrollingParent2 {
        private boolean mFirstStopNestedScroll = true;
        private int mTotalTranslationY;
        private boolean mIsDoFling;

        private InternalView(@NonNull Context context) {
            super(context);
        }

        public InternalView(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, @ViewCompat.ScrollAxis int axes, @ViewCompat.NestedScrollType int type) {
            return !mIsDoingAnim && type == ViewCompat.TYPE_TOUCH && target instanceof NestedScrollingChild;
        }

        @Override
        public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, @ViewCompat.ScrollAxis int axes, @ViewCompat.NestedScrollType int type) {

        }

        @Override
        public void onStopNestedScroll(@NonNull View target, @ViewCompat.NestedScrollType int type) {
            if (mFirstStopNestedScroll) {
                mFirstStopNestedScroll = false;
                return;
            }
            if (!mIsDoFling) {
                checkUpAnim();
            }
            mIsDoFling = false;
            mTotalTranslationY = 0;
            mFirstStopNestedScroll = true;
        }

        @Override
        public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @ViewCompat.NestedScrollType int type) {

        }

        @Override
        public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, @ViewCompat.NestedScrollType int type) {
            if (dy < 0) {
                if (!target.canScrollVertically(-1)) {
                    consumed[0] = 0;
                    consumed[1] = dy;
                    mTotalTranslationY = mTotalTranslationY - dy;
                    if (mTotalTranslationY > mContentView.getHeight()) {
                        mTotalTranslationY = mContentView.getHeight();
                    }
                    mContentView.setTranslationY(mTotalTranslationY);
                }
            } else {
                if (mTotalTranslationY > 0) {
                    consumed[0] = 0;
                    consumed[1] = dy;
                    if (mTotalTranslationY < dy) {
                        mTotalTranslationY = 0;
                    } else {
                        mTotalTranslationY = mTotalTranslationY - dy;
                    }
                    mContentView.setTranslationY(mTotalTranslationY);
                }
            }
        }

        @Override
        public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
            if (velocityY < 0) {
                velocityY = -velocityY;
                if (velocityY > SWIPE_VELOCITY_THRESHOLD && !target.canScrollVertically(-1)) {
                    hide();
                    mTotalTranslationY = 0;
                    mIsDoFling = true;
                    return true;
                }
            } else {
                if (velocityY > SWIPE_VELOCITY_THRESHOLD && !target.canScrollVertically(-1)) {
                    doResetAnim();
                    mTotalTranslationY = 0;
                    mIsDoFling = true;
                    return true;
                }
            }
            return super.onNestedPreFling(target, velocityX, velocityY);
        }
    }

    private class InternalTouchListener implements View.OnTouchListener {
        private float mStartY;
        private GestureDetector mGestureDetector;

        public InternalTouchListener(Context context) {
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    if (velocityY > 0) {
                        if (velocityY > SWIPE_VELOCITY_THRESHOLD) {
                            hide();
                            return true;
                        }
                    } else {
                        velocityY = -velocityY;
                        if (velocityY > SWIPE_VELOCITY_THRESHOLD) {
                            doResetAnim();
                            return true;
                        }
                    }
                    return false;
                }
            });
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mIsDoingAnim) {
                return true;
            }
            boolean detect = mGestureDetector.onTouchEvent(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mStartY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int y = (int) (event.getRawY() - mStartY);
                    if (y < 0) {
                        y = 0;
                    }
                    mContentView.setTranslationY(y);
                    break;
                case MotionEvent.ACTION_UP:
                    if (!detect) {
                        checkUpAnim();
                    }
                    break;
            }
            return true;
        }
    }
}
