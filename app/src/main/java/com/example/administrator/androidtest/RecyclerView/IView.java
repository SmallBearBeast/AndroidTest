package com.example.administrator.androidtest.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class IView extends FrameLayout {
    private static final String TAG = "IView";
    public static final int VIEW_HEAD = 1;
    public static final int VIEW_FOOT = 2;
    public static final int STATE_START = 1;
    public static final int STATE_CHANGE = 2;
    public static final int STATE_CAN_DOING = 3;
    public static final int STATE_DOING = 4;
    public static final int STATE_FINISH = 5;
    protected PullLoadLayout mPullLoadLayout;
    private View mContentView;
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
            mState = STATE_CHANGE;
        }
        if(mState == STATE_CHANGE){
            if(canDoing()){
                mState = STATE_CAN_DOING;
            }
        }
        if(mState == STATE_CAN_DOING){
            if(!canDoing()){
                mState = STATE_CHANGE;
            }
        }
    }

    protected boolean canDoing(){
        return mProgress > 0.9f;
    }

    public void doing(){

    }

    public void finish(){

    }

    public int height(){
        return -1;
    }

    public int layoutId(){
        return -1;
    }

    protected int dp2px(int dpVal){
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }

    protected void attachParrent(PullLoadLayout pullLoadLayout){
        mPullLoadLayout = pullLoadLayout;
    }

    public void setState(int state){
        Log.e(TAG, "setState: " + state);
        mState = state;
    }

    public int getState(){
        return mState;
    }
}
