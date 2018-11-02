package com.example.administrator.androidtest.RecyclerView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.androidtest.R;


public class PullLoadLayout extends FrameLayout{
    private static final String TAG = "PullLoadLayout";
    private static final int TYPE_OUT_MOVE = 1;
    private static final int TYPE_IN_MOVE = 2;
    private static final int TYPE_COVER_MOVE = 3;
    private int mHeadMoveType = TYPE_OUT_MOVE;
    private int mFootMoveType = TYPE_OUT_MOVE;
    private int mHeadFinishTime = 1000;
    private int mFootFinishTime = 1000;
    private IView mHeadView;
    private IView mFootView;
    private View mCenterView;
    private FrameLayout.LayoutParams mHeadLayoutParams;
    private FrameLayout.LayoutParams mFootLayoutParams;
    private FrameLayout.LayoutParams mCenterLayoutParams;
    private boolean mHeadEnable;
    private boolean mFootEnable;
    private int mInitHeadHeight;
    private int mInitFootHeight;
    private int mHeadMargin;
    private int mFootMargin;
    private float mScrollHeadHeight;
    private float mScrollFootHeight;
    private boolean isIntecepted = false;
    private boolean isHeadShow = false;
    private boolean isFootShow = false;

    private PullLoadListener mPullLoadListener;
    private float mStartY = 0;
    private float mEndY = 0;
    private Context mContext;
    public PullLoadLayout(@NonNull Context context) {
        this(context, null);
    }

    public PullLoadLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullLoadLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initTypeArray(attrs);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                initView();
                initLayoutParams();
                adjustViewByType();
                adjustByType();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void initHeight(){
        if(mHeadView != null){
            mInitHeadHeight = mHeadView.height();
            mHeadMargin = -mInitHeadHeight;
            mHeadView.attachParrent(this);
        }
        if(mFootView != null){
            mInitFootHeight = mFootView.height();
            mFootMargin = -mInitFootHeight;
            mFootView.attachParrent(this);
        }
    }

    private void initLayoutParams() {
        if(mHeadView != null){
            mHeadLayoutParams = (LayoutParams) mHeadView.getLayoutParams();
            mHeadLayoutParams.gravity = Gravity.TOP;
            mHeadView.setLayoutParams(mHeadLayoutParams);
        }
        if(mFootView != null){
            mFootLayoutParams = (LayoutParams) mFootView.getLayoutParams();
            mFootLayoutParams.gravity = Gravity.BOTTOM;
            mFootView.setLayoutParams(mFootLayoutParams);
        }
        if(mCenterView != null){
            mCenterLayoutParams = (LayoutParams) mCenterView.getLayoutParams();
        }
    }

    private void adjustViewByType() {
        if(mHeadEnable && mHeadView != null){
            if(mHeadMoveType == TYPE_COVER_MOVE){
                removeView(mHeadView);
                addView(mHeadView);
            }
        }
        if(mFootEnable && mFootView != null){
            if(mFootMoveType == TYPE_IN_MOVE){
                removeView(mFootView);
                addView(mFootView, 0);
            }
        }
    }

    /**
     * 变化高度
     * @param height
     */
    private void adjustFootByType(int height){
        if(mFootMoveType == TYPE_OUT_MOVE){
            adjustFootMargin(height);
            adjustCenterBottomMargin(height);
        }else if(mFootMoveType == TYPE_IN_MOVE){
            adjustCenterBottomMargin(height);
        }else if(mFootMoveType == TYPE_COVER_MOVE){
            adjustFootMargin(height);
        }
    }

    /**
     * 变化高度
     * @param height
     */
    private void adjustHeadByType(int height){
        if(mHeadMoveType == TYPE_OUT_MOVE){
            adjustHeadMargin(height);
            adjustCenterTopMargin(height);
        }else if(mHeadMoveType == TYPE_IN_MOVE){
            adjustCenterTopMargin(height);
        }else if(mHeadMoveType == TYPE_COVER_MOVE){
            adjustHeadMargin(height);
        }
    }

    private void adjustHeadMargin(int height){
        mHeadMargin = mHeadMargin + height;
        if(-mHeadMargin > mInitHeadHeight){
            mHeadMargin = -mInitHeadHeight;
        }else if(-mHeadMargin < 0){
            mHeadMargin = 0;
        }
        mHeadLayoutParams.topMargin = mHeadMargin;
        mHeadView.setLayoutParams(mHeadLayoutParams);
    }

    private void adjustHeadScroll(){
        if(mScrollHeadHeight > mInitHeadHeight){
            mScrollHeadHeight = mInitHeadHeight;
        }else if(mScrollHeadHeight < 0){
            mScrollHeadHeight = 0;
            isHeadShow = false;
            isFootShow = true;
        }
    }

    private void adjustFootScroll(){
        if(-mScrollFootHeight > mInitFootHeight){
            mScrollFootHeight = -mInitFootHeight;
        }else if(-mScrollFootHeight < 0){
            mScrollFootHeight = 0;
            isHeadShow = true;
            isFootShow = false;
        }
    }

    private void adjustFootMargin(int height){
        mFootMargin = mFootMargin - height;
        if(-mFootMargin > mInitFootHeight){
            mFootMargin = -mInitFootHeight;
        }else if(-mFootMargin < 0){
            mFootMargin = 0;
        }
        mFootLayoutParams.bottomMargin = mFootMargin;
        mFootView.setLayoutParams(mFootLayoutParams);
    }

    private void adjustCenterTopMargin(int height){
        mCenterLayoutParams.topMargin = mCenterLayoutParams.topMargin + height;
        if(mCenterLayoutParams.topMargin < 0){
            mCenterLayoutParams.topMargin = 0;
        }else if(mCenterLayoutParams.topMargin > mInitHeadHeight){
            mCenterLayoutParams.topMargin = mInitHeadHeight;
        }
        mCenterView.setLayoutParams(mCenterLayoutParams);
    }

    private void adjustCenterBottomMargin(int height){
        mCenterLayoutParams.bottomMargin = mCenterLayoutParams.bottomMargin - height;
        if(mCenterLayoutParams.bottomMargin < 0){
            mCenterLayoutParams.bottomMargin = 0;
        }else if(mCenterLayoutParams.bottomMargin > mInitFootHeight){
            mCenterLayoutParams.bottomMargin = mInitFootHeight;
        }
        mCenterLayoutParams.topMargin = mCenterLayoutParams.topMargin + height;
        if(mCenterLayoutParams.topMargin > 0){
            mCenterLayoutParams.topMargin = 0;
        }else if(-mCenterLayoutParams.topMargin > mInitFootHeight){
            mCenterLayoutParams.topMargin = -mInitFootHeight;
        }
        mCenterView.setLayoutParams(mCenterLayoutParams);
    }

    private void adjustByType() {
        if(mHeadEnable && mHeadView != null){
            adjustHeadByType(dp2px(0));
        }
        if(mFootEnable && mFootView != null){
            adjustFootByType(dp2px(0));
        }
    }

    private void initTypeArray(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.PullLoadLayout);
        mHeadEnable = typedArray.getBoolean(R.styleable.PullLoadLayout_pdl_head_enable, true);
        mFootEnable = typedArray.getBoolean(R.styleable.PullLoadLayout_pdl_foot_enable, true);
        mHeadMoveType = typedArray.getInteger(R.styleable.PullLoadLayout_pdl_head_type, TYPE_OUT_MOVE);
        mFootMoveType = typedArray.getInteger(R.styleable.PullLoadLayout_pdl_foot_type, TYPE_OUT_MOVE);
        mHeadFinishTime = typedArray.getInteger(R.styleable.PullLoadLayout_pdl_head_time, 1000);
        mFootFinishTime = typedArray.getInteger(R.styleable.PullLoadLayout_pdl_foot_time, 1000);
        typedArray.recycle();
    }

    private void initView() {
        int count = getChildCount();
        View view = null;
        if(count > 3){

        }else if(count == 3){
            if((view = getChildAt(0)) instanceof IView){
                mHeadView = (IView) view;
            }
            mCenterView = getChildAt(1);
            if((view = getChildAt(2)) instanceof IView){
                mFootView = (IView) view;
            }
        }else if(count == 2){
            if((view = getChildAt(0)) instanceof IView){
                mHeadView = (IView) view;
            }else {
                mCenterView = view;
            }
            if((view = getChildAt(1)) instanceof IView){
                mFootView = (IView) view;
            }else {
                mCenterView = view;
            }
        }
        initHeight();
    }

    public void move(float progress, int type){

    }

    private float mInterceptStartY;
    private float mInterceptEndY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        isIntecepted = false;
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            mStartY = ev.getY();
        }else if(ev.getAction() == MotionEvent.ACTION_MOVE){
            mEndY = ev.getY();
            float diff = mEndY - mStartY;
            mStartY = mEndY;
            isIntecepted = false;
            if(mCenterView instanceof ScrollView){
                isIntecepted = PullLoadUtil.isScrollViewIntercept(mCenterView, diff);
            }else if(mCenterView instanceof RecyclerView){
                isIntecepted = PullLoadUtil.isRecyclerViewIntercept(mCenterView, diff);
            }else {
                isIntecepted = false;
            }
        }else if(ev.getAction() == MotionEvent.ACTION_UP){
            isIntecepted = false;
        }
        return isIntecepted;
    }

    private boolean isHeadEnable(){
        return mHeadView != null && mHeadEnable;
    }

    private boolean isHeadScrollEnable(float diff){
        return mScrollHeadHeight > 0 || (mScrollHeadHeight < 0 && mScrollHeadHeight - diff > 0);
    }

    private boolean isFootScrollEnable(float diff){
        return mScrollFootHeight < 0 || (mScrollFootHeight > 0 && mScrollFootHeight - diff < 0);
    }

    private boolean isFootEnable(){
        return mFootView != null && mFootEnable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO: 2018/10/31 计算拖拽高度变化 有正负
        if(isHeadEnable() && mHeadView.mState == IView.STATE_FINISH){
            mStartY = event.getY();
            return true;
        }
        if(isFootEnable() && mFootView.mState == IView.STATE_FINISH){
            mStartY = event.getY();
            return true;
        }
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            mStartY = event.getY();
        }else if(action == MotionEvent.ACTION_MOVE){
            mEndY = event.getY();
            int diff = (int) getSlowDiff(mEndY - mStartY);
            mStartY = mEndY;
            mScrollFootHeight = mScrollFootHeight + diff;
            mScrollHeadHeight = mScrollHeadHeight + diff;
            Log.e(TAG, "mScrollHeadHeight + 1: " + mScrollHeadHeight);
            if(!isFootShow && isHeadEnable() && isHeadScrollEnable(diff) && !PullLoadUtil.isCanRecyclerViewDown(mCenterView, diff)) {
                adjustHeadByType(diff);
                mHeadView.change(calHeadProgress());
                isHeadShow = true;
                isFootShow = false;
            }
            if(!isHeadShow && isFootEnable() && isFootScrollEnable(diff) && !PullLoadUtil.isCanRecyclerViewUp(mCenterView, diff)){
                adjustFootByType(diff);
                mFootView.change(calFootProgress());
                isHeadShow = false;
                isFootShow = true;
            }
            Log.e(TAG, "mScrollHeadHeight + 2: " + mScrollHeadHeight);
            adjustHeadScroll();
            adjustFootScroll();
            Log.e(TAG, "mScrollHeadHeight + 3: " + mScrollHeadHeight);
            if(mCenterView instanceof RecyclerView) {
                if(PullLoadUtil.isCanRecyclerView(mCenterView, diff)){
                    Log.e(TAG, "onTouchEvent: " + true);
                    ((RecyclerView) mCenterView).requestDisallowInterceptTouchEvent(true);
                }
            } else if(mCenterView instanceof ScrollView) {
                if(PullLoadUtil.isCanScrollView(mCenterView, diff)){
                    ((ScrollView) mCenterView).requestDisallowInterceptTouchEvent(true);
                }
            }
        }else if(action == MotionEvent.ACTION_UP){
            if(isHeadEnable() && !isFootShow){
                if(mHeadView.mState == IView.STATE_CAN_DOING){
                    mHeadView.setState(IView.STATE_DOING);
                    mHeadView.doing();
                    mHeadView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            restoreHead();
                        }
                    }, 1000);
                    if(mPullLoadListener != null){
                        mPullLoadListener.onPull();
                    }
                }else {
                    restoreHead();
                }
            }
            if(isFootEnable() && !isHeadShow){
                if(mFootView.mState == IView.STATE_CAN_DOING){
                    mFootView.setState(IView.STATE_DOING);
                    mFootView.doing();
                    mFootView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            restoreFoot();
                        }
                    }, 1000);
                    if(mPullLoadListener != null){
                        mPullLoadListener.onLoad();
                    }
                }else {
                    restoreFoot();
                }
            }
        }
        return true;
    }

    private float calHeadProgress() {
        float progress = 0f;
        if(mHeadMoveType == TYPE_OUT_MOVE){
            progress = (mInitHeadHeight + mHeadMargin) * 1.0f / mInitHeadHeight;
        }else if(mHeadMoveType == TYPE_IN_MOVE){
            progress = mCenterLayoutParams.topMargin * 1.0f / mInitHeadHeight;
        }else if(mHeadMoveType == TYPE_COVER_MOVE){
            progress = (mInitHeadHeight + mHeadMargin) * 1.0f / mInitHeadHeight;
        }
        return progress;
    }

    private float calFootProgress() {
        float progress = 0f;
        if(mFootMoveType == TYPE_OUT_MOVE){
            progress = (mInitFootHeight + mFootMargin) * 1.0f / mInitFootHeight;
        }else if(mFootMoveType == TYPE_IN_MOVE){
            progress = mCenterLayoutParams.bottomMargin * 1.0f / mInitFootHeight;
        }else if(mFootMoveType == TYPE_COVER_MOVE){
            progress = (mInitFootHeight + mFootMargin) * 1.0f / mInitFootHeight;
        }
        return progress;
    }

    private void resetFoot(){
        Log.e(TAG, "Foot: " + "finish" );
        mFootView.setState(IView.STATE_START);
        mScrollFootHeight = 0;
        isFootShow = false;
    }

    private void restoreFoot() {
        mFootView.setState(IView.STATE_FINISH);
        if(mFootMoveType == TYPE_OUT_MOVE){
            restoreFootOutMove();
        }else if(mFootMoveType == TYPE_IN_MOVE){
            restoreFootInMove();
        }else if(mFootMoveType == TYPE_COVER_MOVE){
            restoreFootCoverMove();
        }
    }

    private void restoreFootCoverMove() {
        ValueAnimator animator = ValueAnimator.ofInt(mFootMargin, -mInitFootHeight);
        animator.setDuration((long) (mFootView.mProgress * mFootFinishTime));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFootMargin = (int) animation.getAnimatedValue();
                mFootLayoutParams.bottomMargin = mFootMargin;
                mFootView.setLayoutParams(mFootLayoutParams);
                if(-mFootMargin == mInitFootHeight && mFootView.getState() == IView.STATE_FINISH){
                    resetFoot();
                }
            }
        });
        animator.start();
    }

    private void restoreFootInMove() {
        ValueAnimator animator = ValueAnimator.ofInt(mCenterLayoutParams.bottomMargin, 0);
        animator.setDuration((long) (mFootView.mProgress * mFootFinishTime));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCenterLayoutParams.bottomMargin = (int) animation.getAnimatedValue();
                mCenterView.setLayoutParams(mCenterLayoutParams);
                if(-mFootMargin == mInitFootHeight && mFootView.getState() == IView.STATE_FINISH){
                    resetFoot();
                }
            }
        });
        animator.start();
    }

    private void restoreFootOutMove() {
        ValueAnimator animator = ValueAnimator.ofInt(mFootMargin, -mInitFootHeight);
        animator.setDuration((long) (mFootView.mProgress * mFootFinishTime));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFootMargin = (int) animation.getAnimatedValue();
                mFootLayoutParams.bottomMargin = mFootMargin;
                mFootView.setLayoutParams(mFootLayoutParams);
                mCenterLayoutParams.bottomMargin = mInitFootHeight + mFootMargin;
                mCenterLayoutParams.topMargin = -mInitFootHeight - mFootMargin;
                mCenterView.setLayoutParams(mCenterLayoutParams);
                if(-mFootMargin == mInitFootHeight && mFootView.getState() == IView.STATE_FINISH){
                    resetFoot();
                }
            }
        });
        animator.start();
    }

    private void restoreHead(){
        mHeadView.setState(IView.STATE_FINISH);
        if(mHeadMoveType == TYPE_OUT_MOVE){
            restoreHeadOutMove();
        }else if(mHeadMoveType == TYPE_IN_MOVE){
            restoreHeadInMove();
        }else if(mHeadMoveType == TYPE_COVER_MOVE){
            restoreHeadCoverMove();
        }
    }

    private void resetHead(){
        Log.e(TAG, "Head: " + "finish" );
        mHeadView.setState(IView.STATE_START);
        mScrollHeadHeight = 0;
        isHeadShow = false;
    }

    private void restoreHeadCoverMove() {
        ValueAnimator animator = ValueAnimator.ofInt(mHeadMargin, -mInitHeadHeight);
        animator.setDuration((long) (mHeadView.mProgress * mHeadFinishTime));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mHeadMargin = (int) animation.getAnimatedValue();
                mHeadLayoutParams.topMargin = mHeadMargin;
                mHeadView.setLayoutParams(mHeadLayoutParams);
                if(-mHeadMargin == mInitHeadHeight && mHeadView.getState() == IView.STATE_FINISH){
                    resetHead();
                }
            }
        });
        animator.start();
    }

    private void restoreHeadInMove(){
        ValueAnimator animator = ValueAnimator.ofInt(mCenterLayoutParams.topMargin, 0);
        animator.setDuration((long) (mHeadView.mProgress * mHeadFinishTime));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCenterLayoutParams.topMargin = (int) animation.getAnimatedValue();
                mCenterView.setLayoutParams(mCenterLayoutParams);
                if(mCenterLayoutParams.topMargin == 0 && mHeadView.getState() == IView.STATE_FINISH){
                    resetHead();
                }
            }
        });
        animator.start();
    }

    private void restoreHeadOutMove() {
        mHeadView.setState(IView.STATE_FINISH);
        ValueAnimator animator = ValueAnimator.ofInt(mHeadMargin, -mInitHeadHeight);
        animator.setDuration((long) (mHeadView.mProgress * mHeadFinishTime));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mHeadMargin = (int) animation.getAnimatedValue();
                mHeadLayoutParams.topMargin = mHeadMargin;
                mHeadView.setLayoutParams(mHeadLayoutParams);
                mCenterLayoutParams.topMargin = mInitHeadHeight + mHeadMargin;
                mCenterView.setLayoutParams(mCenterLayoutParams);
                if(-mHeadMargin == mInitHeadHeight && mHeadView.getState() == IView.STATE_FINISH){
                    resetHead();
                }
            }
        });
        animator.start();
    }

    private int dp2px(int dpVal){
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }

    interface PullLoadListener{
        void onPull();

        void onLoad();
    }

    private float getSlowDiff(float diff){
        return diff / 2;
    }
}
