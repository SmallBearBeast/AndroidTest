package com.example.administrator.androidtest.RecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.administrator.androidtest.R;

public class PullLoadLayout extends FrameLayout{
    private IView mHeadView;
    private IView mFootView;
    private boolean mHeadEnable;
    private boolean mFootEnable;
    private int mPullLoadType;
    private int mHeadHeight;
    private int mFootHeight;
    private PullLoadListener mPullLoadListener;
    private Context mContext;
    public PullLoadLayout(@NonNull Context context) {
        super(context, null);
    }

    public PullLoadLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public PullLoadLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initTypeArray(attrs);
        initView();
        adjustByType();
    }

    private void adjustByType() {
        if(mHeadEnable && mHeadView != null){
            mHeadHeight = mHeadView.height();

            
        }
    }

    private void initTypeArray(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.PullLoadLayout);
        mHeadEnable = typedArray.getBoolean(R.styleable.PullLoadLayout_pdl_head_enable, true);
        mFootEnable = typedArray.getBoolean(R.styleable.PullLoadLayout_pdl_foot_enable, true);
        mPullLoadType = typedArray.getInteger(R.styleable.PullLoadLayout_pdl_type, 1);
        typedArray.recycle();
    }

    private void initView() {
        int count = getChildCount();
        View view = null;
        if(count > 3){

        }else if(count == 3){
            if((view = getChildAt(0)) instanceof IView){
                mHeadView = (IView) view;
            }else if((view = getChildAt(2)) instanceof IView){
                mFootView = (IView) view;
            }
        }else if(count == 2){
            if((view = getChildAt(0)) instanceof IView){
                mHeadView = (IView) view;
            }else if((view = getChildAt(1)) instanceof IView){
                mFootView = (IView) view;
            }
        }
    }

    public void move(float progress, int type){

    }

    interface PullLoadListener<T>{
        void onPull(T data);

        void onLoad(T data);
    }

    static class IView extends FrameLayout{
        private static final int VIEW_HEAD = 1;
        private static final int VIEW_FOOT = 2;
        private static final int STATE_START = 1;
        private static final int STATE_CHANGE = 2;
        private static final int STATE_DOING = 3;
        private static final int STATE_FINISH = 4;
        private View mContentView;
        private Context mContext;
        protected int mState;
        protected float mProgress;
        protected int mHeight;

        public IView(@NonNull Context context) {
            super(context, null);
        }

        public IView(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs, 0);
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
            mProgress = progress;
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

    }
}
