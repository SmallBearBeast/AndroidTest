package com.example.administrator.androidtest.Test.RecyclerView;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public abstract class IView extends FrameLayout {
    private static final String TAG = "IView";
    public static final int VIEW_HEAD = 1;
    public static final int VIEW_FOOT = 2;
    public static final int STATE_START = 1;
    public static final int STATE_CHANGE = 2;
    public static final int STATE_CAN_DOING = 3;
    public static final int STATE_DOING = 4;
    public static final int STATE_FINISH = 5;
    protected PullLoadLayout mPullLoadLayout;
    protected View mContentView;
    private Context mContext;
    protected int mState = STATE_START;
    protected float mProgress;
    protected int mHeight;

    public IView(@NonNull Context context) {
        this(context, null);
    }

    public IView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mHeight = height();
        initView();
    }

    private void initView(){
        mContentView = LayoutInflater.from(mContext).inflate(layoutId(), null);
        addView(mContentView);
    }

    public void change(float progress){
        Log.e(TAG, "change: " + progress);
        mProgress = progress;
        if(mState == STATE_START){
            setState(STATE_CHANGE);
        }
        if(mState == STATE_CHANGE){
            if(canDoing()){
                setState(STATE_CAN_DOING);
            }
        }
        if(mState == STATE_CAN_DOING){
            if(!canDoing()){
                setState(STATE_CHANGE);
            }
        }
    }

    protected boolean canDoing(){
        return mProgress == 1f;
    }

    public void doing(){

    }

    public abstract int height();

    public abstract int layoutId();

    protected int dp2px(int dpVal){
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }

    public void attachParrent(PullLoadLayout pullLoadLayout){
        mPullLoadLayout = pullLoadLayout;
    }

    public void setState(int state){
        Log.e(TAG, "setState: " + state);
        mState = state;
    }

    public int getState(){
        return mState;
    }

    /**
     * Finish状态和Doing状态不处理事件
     */
    public boolean isIntercept(){
        return mState == IView.STATE_FINISH || mState == IView.STATE_DOING;
    }
}
