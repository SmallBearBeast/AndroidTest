package com.example.libframework.CoreUI;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.libframework.BuildConfig;
import com.example.libframework.Page.IPage;
import com.example.libframework.Page.Page;
import com.example.libframework.Page.PageProvider;
import com.example.liblog.SLog;

import java.util.List;

public abstract class BaseFrag extends Fragment implements IPage {
    protected String TAG = getClass().getSimpleName();
    //Preserve the position of the last visible sub fragment.
    private int mLastVisibleFragPos = 0;
    private boolean mIsDoneSetUserVisibleHint;
    private boolean mIsVisibleToUser;
    private boolean mIsDoneStart;
    private Page mPage;
    protected BaseAct mBaseAct;
    protected BaseFrag mBaseFrag;

    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getContext() instanceof BaseAct) {
            mBaseAct = (BaseAct) getContext();
        }
        if (getParentFragment() instanceof BaseFrag) {
            mBaseFrag = (BaseFrag) getParentFragment();
        }
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            getLifecycle().addObserver(new FragLifeDebug(getClass().getSimpleName()));
        }
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
    @CallSuper
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutId(), container, false);
    }

    @Override
    @CallSuper
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
    @CallSuper
    public void onAttachFragment(Fragment childFragment) {
        boolean isVisibleToUser = getUserVisibleHint();
        if (!isVisibleToUser) {
            if (childFragment.getUserVisibleHint()) {
                childFragment.setUserVisibleHint(false);
            }
        }
    }

    @Override
    @CallSuper
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
    @CallSuper
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            PageProvider.getInstance().addPage(mBaseAct.getPage(), createPage());
        }
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        mPage = null;
    }

    @Override
    @CallSuper
    public void onDetach() {
        super.onDetach();
        mBaseAct = null;
        mBaseFrag = null;
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

    protected <T extends View> T findViewById(@IdRes int viewId) {
        if (getView() != null) {
            return getView().findViewById(viewId);
        }
        return null;
    }

    @Override
    public int pageId() {
        return 0;
    }
}
