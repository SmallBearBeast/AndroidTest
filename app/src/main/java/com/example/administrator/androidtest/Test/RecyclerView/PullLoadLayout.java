package com.example.administrator.androidtest.Test.RecyclerView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ScrollView;

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

    private PullLoadListener mPullLoadListener;
    private float mStartY = 0; //onInterceptTouchEvent()拦截以后就不会在onTouchEvent()走ACTION_DOWN方法
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
                addView(mHeadView, mHeadLayoutParams);
            }
        }
        if(mFootEnable && mFootView != null){
            if(mFootMoveType == TYPE_IN_MOVE){
                removeView(mFootView);
                addView(mFootView, 0, mFootLayoutParams);
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

    /**
     * 调整mScrollHeadHeight大小
     */
    private void adjustHeadScroll(){
        if(mScrollHeadHeight > mInitHeadHeight){
            mScrollHeadHeight = mInitHeadHeight;
        }else if(mScrollHeadHeight < 0){
            mScrollHeadHeight = 0;
        }
    }

    /**
     * 调整mScrollFootHeight大小
     */
    private void adjustFootScroll(){
        if(-mScrollFootHeight > mInitFootHeight){
            mScrollFootHeight = -mInitFootHeight;
        }else if(-mScrollFootHeight < 0){
            mScrollFootHeight = 0;
        }
        Log.d(TAG, "tag_1: adjustFootScroll()->mScrollFootHeight = " + mScrollFootHeight);
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
        Log.d(TAG, "tag_1: mCenterLayoutParams.bottomMargin = " + mCenterLayoutParams.bottomMargin + " height = " + height);
        if(mCenterLayoutParams.bottomMargin < 0){
            mCenterLayoutParams.bottomMargin = 0;
        }else if(mCenterLayoutParams.bottomMargin > mInitFootHeight){
            mCenterLayoutParams.bottomMargin = mInitFootHeight;
        }

        //这边需要设置mCenterLayoutParams.topMargin，mCenterLayoutParams.bottomMargin增加多少，mCenterLayoutParams.topMargin就减少多少。
        //这样才能保持mCenterView高度不变，不为下面的计算带来干扰。
        mCenterLayoutParams.topMargin = mCenterLayoutParams.topMargin + height;
        if(mCenterLayoutParams.topMargin > 0){
            mCenterLayoutParams.topMargin = 0;
        }else if(-mCenterLayoutParams.topMargin > mInitHeadHeight){
            mCenterLayoutParams.topMargin = -mInitHeadHeight;
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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        isIntecepted = false;
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            Log.d(TAG, "onInterceptTouchEvent:" + "MotionEvent.ACTION_DOWN");
            mStartY = ev.getY();
        }else if(ev.getAction() == MotionEvent.ACTION_MOVE){
            float endY = ev.getY();
            float diff = endY - mStartY;
            mStartY = endY;
            if(mCenterView instanceof ScrollView){
                isIntecepted = PullLoadUtil.isScrollViewIntercept(mCenterView, diff);
            }else if(mCenterView instanceof RecyclerView){
                isIntecepted = PullLoadUtil.isRecyclerViewIntercept(mCenterView, diff);
            }else {
                isIntecepted = false;
            }
            Log.d(TAG, "onInterceptTouchEvent:" + "MotionEvent.ACTION_MOVE" + isIntecepted);
        }else if(ev.getAction() == MotionEvent.ACTION_UP){
            isIntecepted = false;
            Log.d(TAG, "onInterceptTouchEvent:" + "MotionEvent.ACTION_UP");
        }
        Log.e(TAG, "onInterceptTouchEvent: " + isIntecepted);
        return isIntecepted;
    }

    private boolean isHeadEnable(){
        return mHeadView != null && mHeadEnable;
    }

    /**
     * 判断是否是HeadView在滚动状态，需要注意滚动过快的情况
     */
    private boolean isHeadScrollEnable(float diff){
        return mScrollHeadHeight > 0 || (mScrollHeadHeight < 0 && mScrollHeadHeight - diff > 0);
    }

    /**
     * 判断是否是FootView在滚动状态，需要注意滚动过快的情况
     */
    private boolean isFootScrollEnable(float diff){
        Log.d(TAG, "tag_1: isFootScrollEnable()->mScrollFootHeight = " + mScrollFootHeight + " diff = " + diff);
        Log.d(TAG, "tag_1: isFootScrollEnable()-> " + (mScrollFootHeight < 0 || (mScrollFootHeight > 0 && mScrollFootHeight - diff < 0)));
        return mScrollFootHeight < 0 || (mScrollFootHeight > 0 && mScrollFootHeight - diff < 0);
    }

    private boolean isFootEnable(){
        return mFootView != null && mFootEnable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //当headView和footView处于doing状态不处理事件
        if((isHeadEnable() && mHeadView.isIntercept()) || (isFootEnable() && mFootView.isIntercept())){
            return true;
        }
        int action = event.getAction();
        //onInterceptTouchEvent()拦截以后就不会在onTouchEvent()走ACTION_DOWN方法，所以省略掉在ACTION_DOWN里面获取mStartY的值
        float endY = event.getY();
        int diff = (int) getSlowDiff(endY - mStartY);
        mStartY = endY;
        if(action == MotionEvent.ACTION_MOVE){
            Log.d(TAG, "tag_2: onTouchEvent()->mScrollHeadHeight = " + mScrollHeadHeight + " diff = " + diff);
            mScrollFootHeight = mScrollFootHeight + diff;
            mScrollHeadHeight = mScrollHeadHeight + diff;
            if(isHeadEnable() && isHeadScrollEnable(diff) && !PullLoadUtil.isViewScrollDown(mCenterView)) {
                adjustHeadByType(diff);
                mHeadView.change(calHeadProgress());
            }else if(isFootEnable() && isFootScrollEnable(diff) && !PullLoadUtil.isViewScrollUp(mCenterView)){
                adjustFootByType(diff);
                mFootView.change(calFootProgress());
            }
            adjustHeadScroll();
            adjustFootScroll();
        }else if(action == MotionEvent.ACTION_UP){
            if(isHeadEnable() && isHeadScrollEnable(diff) && !PullLoadUtil.isViewScrollDown(mCenterView)){
                Log.d(TAG, "tag_2: mScrollHeadHeight = " + mScrollHeadHeight + " diff = " + diff);
                if(mHeadView.mState == IView.STATE_CAN_DOING){
                    mHeadView.setState(IView.STATE_DOING);
                    mHeadView.doing();
                    if(mPullLoadListener != null){
                        mPullLoadListener.onPull();
                    }else {
                        //模拟下拉刷新耗时操作
                        mHeadView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                restoreHead();
                            }
                        }, 1000);
                    }
                }else {
                    restoreHead();
                }
            }else if(isFootEnable() && isFootScrollEnable(diff)  && !PullLoadUtil.isViewScrollUp(mCenterView)){
                Log.d(TAG, "tag_2: mScrollFootHeight = " + mScrollFootHeight + " diff = " + diff);
                if(mFootView.mState == IView.STATE_CAN_DOING){
                    mFootView.setState(IView.STATE_DOING);
                    mFootView.doing();
                    if(mPullLoadListener != null){
                        mPullLoadListener.onLoad();
                    }else {
                        //模拟上拉加载耗时操作
                        mFootView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                restoreFoot();
                            }
                        }, 1000);
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
        final ValueAnimator animator = ValueAnimator.ofInt(mFootMargin, -mInitFootHeight);
        animator.setDuration((long) (mFootView.mProgress * mFootFinishTime));
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFootMargin = (int) animation.getAnimatedValue();
                mFootLayoutParams.bottomMargin = mFootMargin;
                mFootView.setLayoutParams(mFootLayoutParams);
                mFootView.change(calFootProgress());
                if(-mFootMargin == mInitFootHeight && mFootView.getState() == IView.STATE_FINISH){
                    resetFoot();
                    animator.removeUpdateListener(this);
                }
            }
        });
        animator.start();
    }

    private void restoreFootInMove() {
        Log.d(TAG, "restoreFootInMove: mCenterLayoutParams.bottomMargin = " + mCenterLayoutParams.bottomMargin);
        final ValueAnimator animator = ValueAnimator.ofInt(mCenterLayoutParams.bottomMargin, 0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration((long) (mFootView.mProgress * mFootFinishTime));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCenterLayoutParams.topMargin = -(int) animation.getAnimatedValue();
                mCenterLayoutParams.bottomMargin = (int) animation.getAnimatedValue();
                mCenterView.setLayoutParams(mCenterLayoutParams);
                mFootView.change(calFootProgress());
                if(-mFootMargin == mInitFootHeight && mFootView.getState() == IView.STATE_FINISH){
                    resetFoot();
                    animator.removeUpdateListener(this);
                }
            }
        });
        animator.start();
    }

    private void restoreFootOutMove() {
        final ValueAnimator animator = ValueAnimator.ofInt(mFootMargin, -mInitFootHeight);
        animator.setDuration((long) (mFootView.mProgress * mFootFinishTime));
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFootMargin = (int) animation.getAnimatedValue();
                mFootLayoutParams.bottomMargin = mFootMargin;
                mFootView.setLayoutParams(mFootLayoutParams);
                mCenterLayoutParams.bottomMargin = mInitFootHeight + mFootMargin;
                mCenterLayoutParams.topMargin = -mInitFootHeight - mFootMargin;
                mCenterView.setLayoutParams(mCenterLayoutParams);
                mFootView.change(calFootProgress());
                if(-mFootMargin == mInitFootHeight && mFootView.getState() == IView.STATE_FINISH){
                    resetFoot();
                    animator.removeUpdateListener(this);
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
    }

    private void restoreHeadCoverMove() {
        final ValueAnimator animator = ValueAnimator.ofInt(mHeadMargin, -mInitHeadHeight);
        animator.setDuration((long) (mHeadView.mProgress * mHeadFinishTime));
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mHeadMargin = (int) animation.getAnimatedValue();
                mHeadLayoutParams.topMargin = mHeadMargin;
                mHeadView.setLayoutParams(mHeadLayoutParams);
                mHeadView.change(calHeadProgress());
                if(-mHeadMargin == mInitHeadHeight && mHeadView.getState() == IView.STATE_FINISH){
                    resetHead();
                    animator.removeUpdateListener(this);
                }
            }
        });
        animator.start();
    }

    private void restoreHeadInMove(){
        final ValueAnimator animator = ValueAnimator.ofInt(mCenterLayoutParams.topMargin, 0);
        animator.setDuration((long) (mHeadView.mProgress * mHeadFinishTime));
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCenterLayoutParams.topMargin = (int) animation.getAnimatedValue();
                mCenterView.setLayoutParams(mCenterLayoutParams);
                mHeadView.change(calHeadProgress());
                if(mCenterLayoutParams.topMargin == 0 && mHeadView.getState() == IView.STATE_FINISH){
                    resetHead();
                    animator.removeUpdateListener(this);
                }
            }
        });
        animator.start();
    }

    private void restoreHeadOutMove() {
        final ValueAnimator animator = ValueAnimator.ofInt(mHeadMargin, -mInitHeadHeight);
        animator.setDuration((long) (mHeadView.mProgress * mHeadFinishTime));
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mHeadMargin = (int) animation.getAnimatedValue();
                mHeadLayoutParams.topMargin = mHeadMargin;
                mHeadView.setLayoutParams(mHeadLayoutParams);
                mCenterLayoutParams.topMargin = mInitHeadHeight + mHeadMargin;
                mCenterView.setLayoutParams(mCenterLayoutParams);
                mHeadView.change(calHeadProgress());
                if(-mHeadMargin == mInitHeadHeight && mHeadView.getState() == IView.STATE_FINISH){
                    resetHead();
                    animator.removeUpdateListener(this);
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
