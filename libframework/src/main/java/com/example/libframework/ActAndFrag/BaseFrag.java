package com.example.libframework.ActAndFrag;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.libframework.BuildConfig;
import com.example.libframework.Component.FragLifeDebug;
import com.example.libframework.Page.IPage;
import com.example.libframework.Page.Page;
import com.example.libframework.Page.PageProvider;
import com.example.liblog.SLog;

import java.util.List;

public abstract class BaseFrag extends Fragment implements IPage {
    private static String TAG = "BaseFrag";
    //Preserve the position of the last visible sub fragment.
    private int mLastVisibleFragPos = 0;
    private boolean mIsDoneSetUserVisibleHint;
    private boolean mIsVisibleToUser;
    private boolean mIsDoneStart;
    private Page mPage;
    protected BaseAct mBaseAct;
    protected BaseFrag mBaseFrag;
    protected View mContentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContext() instanceof BaseAct) {
            mBaseAct = (BaseAct) getContext();
        }
        if (getParentFragment() instanceof BaseFrag) {
            mBaseFrag = (BaseFrag) getParentFragment();
        }
//        if (BuildConfig.DEBUG) {
//
//        }
        Intent intent = mBaseAct.getIntent();
        if (intent != null) {
            handleIntent(intent, intent.getBundleExtra(IContext.BUNDLE));
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            handleArgument(bundle);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(layoutId(), container, false);
            init(savedInstanceState);
        }
        return mContentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsDoneSetUserVisibleHint = true;
        mIsVisibleToUser = isVisibleToUser;
        if (getActivity() != null) {
            if (mIsDoneStart && mIsVisibleToUser) {
                addPage(null);
                onNotifyVisible();
            }
            List<Fragment> childs = getChildFragmentManager().getFragments();
            boolean hasChild = (childs != null && childs.size() > 0);
            if (hasChild) {
                for (int i = 0; i < childs.size(); i++) {
                    Fragment f = childs.get(i);
                    if (f.getUserVisibleHint()) {
                        mLastVisibleFragPos = i;
                    }
                    if (mLastVisibleFragPos == i) {
                        f.setUserVisibleHint(isVisibleToUser);
                    }
                }
            }
        }
    }

    /**
     * The method will only be called if there are nested fragments in the fragment.
     * Solve the problem which the visibility of multiple child fragments is true during initialization.
     * The basis is that as long as the visibility of the parent fragment is false, the visibility of the child fragment is also false
     */
    @Override
    public void onAttachFragment(Fragment childFragment) {
        boolean isVisibleToUser = getUserVisibleHint();
        if (!isVisibleToUser) {
            if (childFragment.getUserVisibleHint()) {
                childFragment.setUserVisibleHint(false);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mIsDoneStart = true;
        if (mIsDoneSetUserVisibleHint) {
            if (mIsVisibleToUser) {
                addPage(null);
                onNotifyVisible();
            }
        } else {
            addPage(null);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            PageProvider.getInstance().addPage(mBaseAct.getPage(), createPage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPage = null;
        if (BuildConfig.DEBUG) {
            if (mBaseAct != null) {
//                mBaseAct.getSupportFragmentManager().registerFragmentLifecycleCallbacks();
            }
        }
    }

    private Page createPage() {
        mPage = new Page(pageId());
        return mPage;
    }

    public Page getPage() {
        return mPage;
    }

    private void addPage(Page backPage) {
        if (mBaseFrag != null) {
            PageProvider.getInstance().addPage(mBaseFrag.getPage(), backPage != null ? backPage : createPage());
        } else {
            PageProvider.getInstance().addPage(mBaseAct.getPage(), backPage != null ? backPage : createPage());
        }
    }

    protected void handleIntent(@NonNull Intent intent, @Nullable Bundle bundle) {

    }

    protected void handleArgument(@NonNull Bundle bundle) {

    }

    protected abstract int layoutId();

    protected abstract void init(Bundle savedInstanceState);

    /**
     * When the fragment is visible, the method is called.
     * Order: setUserVisibleHint->onAttachFragment->onStart
     */
    protected void onNotifyVisible() {
        SLog.d(TAG, "class = " + getClass().getSimpleName() + "   " + "onNotifyVisible");
    }

    /**
     * Put shared data for easy access by other components.
     *
     * @param key   The name of shared data.
     * @param value The value of shared data.
     */
    protected void put(String key, Object value) {
        ViewModelProviders.of(mBaseAct).get(ShareDataVM.class).put(key, value);
    }

    /**
     * Get the value corresponding to the key
     *
     * @param key The name of shared data.
     * @return The value of shared data.
     */
    protected <V> V get(String key) {
        return ViewModelProviders.of(mBaseAct).get(ShareDataVM.class).get(key);
    }
}
