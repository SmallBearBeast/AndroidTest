package com.example.administrator.androidtest.Common.CaseHelper;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用于无网络，无内容，特殊数据展示
 * 添加新类型比如 {@link #TYPE_FEEDS_NO_POSTS}
 * 在getContainerView获取View，视情况复用View{@link #getContainerView()}
 * 在fillContainerByType填充View，设置点击事件，视情况复用填充{@link #fillContainerByType()}
 * 资源释放资源？？？？{@link #mListenerSparseArray}
 * @author wuyisong on 2019/5/21.
 */

public class CaseHelper {
    public static final int TYPE_NO_NETWORK_IN_FEED = 1; //Feed页无网络
    public static final int TYPE_NO_NETWORK_IN_PERSON = TYPE_NO_NETWORK_IN_FEED + 1; //Person页无网络
    public static final int TYPE_NO_NETWORK_IN_FOLLOW = TYPE_NO_NETWORK_IN_PERSON + 1; //Follow页无网络
    public static final int TYPE_NO_MY_POST = TYPE_NO_NETWORK_IN_FOLLOW + 1; //自己无帖子
    public static final int TYPE_NO_MY_LIKE = TYPE_NO_MY_POST + 1; //自己无点赞
    public static final int TYPE_NO_OTHER_POST = TYPE_NO_MY_LIKE + 1; //他人无帖子
    public static final int TYPE_NO_OTHER_LIKE = TYPE_NO_OTHER_POST + 1; //他人无点赞
    public static final int TYPE_NO_FOLLOWERS = TYPE_NO_OTHER_LIKE + 1; //无关注
    public static final int TYPE_NO_FANS = TYPE_NO_FOLLOWERS + 1; //无粉丝
    public static final int TYPE_FEEDS_NO_POSTS = TYPE_NO_FANS + 1; //没有帖子(follow/hot流使用，如果个人页也一样的话，可以都用这个)


    @IntDef(value = {TYPE_NO_NETWORK_IN_FEED, TYPE_NO_NETWORK_IN_PERSON, TYPE_NO_MY_POST
            , TYPE_NO_MY_LIKE, TYPE_NO_OTHER_POST, TYPE_NO_OTHER_LIKE
            , TYPE_NO_NETWORK_IN_FOLLOW, TYPE_NO_FOLLOWERS, TYPE_NO_FANS, TYPE_FEEDS_NO_POSTS})
    public @interface Type {
    }

    private boolean mIsShow = false;
    private @Type
    int mCurrentType = TYPE_NO_NETWORK_IN_FEED;
    private View mContentView;
    private ViewGroup mContainer;
    private Context mContext;
    private SparseArray<OnClickListener> mListenerSparseArray = new SparseArray<>();

    public CaseHelper(Context context, ViewGroup container) {
        mContext = context;
        mContainer = container;
    }

    public void show(@Type int type, OnClickListener listener){
        if (mContainer == null) {
            return;
        }
        if (mCurrentType != type) {
            if(mContentView != null && mContentView.getParent() == mContainer){
                mContainer.removeView(mContentView);
            }
            mContentView = null;
            mCurrentType = type;
            setOnclickListener(listener, type);
        }
        initContainer();
        mIsShow = true;
    }

    public void show(@Type int type) {
        show(type, null);
    }

    private void initContainer() {
        if (mContentView == null) {
            mContentView = getContainerView();
            fillContainerByType();
            mContainer.addView(mContentView);
        } else {
            fillContainerByType();
        }
        mContentView.setVisibility(View.VISIBLE);
    }

    private View getContainerView() {
        View view = null;
        switch (mCurrentType) {
            case TYPE_NO_NETWORK_IN_FEED:
            case TYPE_NO_NETWORK_IN_PERSON:
            case TYPE_NO_NETWORK_IN_FOLLOW:
                break;

            case TYPE_NO_MY_LIKE:
            case TYPE_NO_OTHER_POST:
            case TYPE_NO_OTHER_LIKE:
            case TYPE_NO_FOLLOWERS:
            case TYPE_NO_FANS:
            case TYPE_FEEDS_NO_POSTS:
                break;

            case TYPE_NO_MY_POST:
                break;
        }
        return view;
    }

    private void fillContainerByType() {
        switch (mCurrentType) {
            case TYPE_NO_NETWORK_IN_FEED:
            case TYPE_NO_NETWORK_IN_FOLLOW:
            case TYPE_NO_NETWORK_IN_PERSON:
                break;

            case TYPE_NO_MY_LIKE:
                break;

            case TYPE_NO_OTHER_POST:
                break;

            case TYPE_NO_OTHER_LIKE:
                break;

            case TYPE_FEEDS_NO_POSTS:
                break;

            case TYPE_NO_FOLLOWERS:
                break;

            case TYPE_NO_FANS:
                break;

            case TYPE_NO_MY_POST:
                setContentMyPost();
                break;

            default:
                break;
        }
    }

    private void setContentMyPost() {

    }

    private void setNoNetWorkContent(@StringRes int strId, @DrawableRes int drawableId) {

    }

    private void onCaseHelperClick(@Type int... types){
        for (int i = 0; i < types.length; i++) {
            OnClickListener listener = mListenerSparseArray.get(types[i]);
            if(listener != null){
                listener.onClick();
                break;
            }
        }
    }

    private void setNoContent(@StringRes int strId, @DrawableRes int drawableId, float textSizeInSp) {

    }

    public void hide() {
        if (mContentView == null || !mIsShow) {
            return;
        }
        mIsShow = false;
        mContentView.setVisibility(View.GONE);
    }

    public void setOnclickListener(OnClickListener listener, @Type int... types) {
        if(listener != null) {
            for (int i = 0; i < types.length; i++) {
                mListenerSparseArray.put(types[i], listener);
            }
        }
    }

    public interface OnClickListener {
        void onClick();
    }
}
