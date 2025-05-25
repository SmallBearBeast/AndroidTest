package com.bear.libbase.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.bear.libbase.BackPressedHelper;
import com.example.libbase.R;
import com.bear.libbase.activity.BaseActivity;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {
    protected String TAG = getClass().getSimpleName();
    private boolean firstVisible = true;
    private BaseActivity baseAct;
    private BaseFragment baseFrag;

    private Toolbar toolbar;
    private VB viewBinding;

    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getContext() instanceof BaseActivity) {
            baseAct = (BaseActivity) getContext();
            Intent intent = baseAct.getIntent();
            if (intent != null) {
                handleIntent(intent);
            }
        }
        if (getParentFragment() instanceof BaseFragment) {
            baseFrag = (BaseFragment) getParentFragment();
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
        View view = layoutView();
        if (view == null && layoutId() != -1) {
            view = inflater.inflate(layoutId(), container, false);
        }
        if (view == null) {
            viewBinding = inflateViewBinding(inflater, container);
            if (viewBinding != null) {
                view = viewBinding.getRoot();
            }
        }
        if (view != null) {
            toolbar = view.findViewById(R.id.lib_base_toolbar_id);
            if (toolbar != null) {
                // 若没有则，setSupportActionbar，则onCreateOptionsMenu不会回调。
                // 若调用setSupportActionBar，则在onCreateOptionsMenu(Menu menu)中获取menu引用，否则直接Menu menu = mToolbar.getMenu();
                getBaseAct().setSupportActionBar(toolbar);
                // 添加Options Menu时，需要额外调用setHasOptionsMenu(true);方法，确保onCreateOptionsMenu()方法得以调用。
                setHasOptionsMenu(true);
            }
        }
        if (view != null) {
            initViews(view);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dispatchFirstVisible();
    }

    @Override
    @CallSuper
    public void onDetach() {
        super.onDetach();
        baseAct = null;
        baseFrag = null;
    }

    protected void handleIntent(@NonNull Intent intent) {

    }

    protected void handleArgument(@NonNull Bundle bundle) {

    }

    protected void initViews(@NonNull View view) {

    }

    private void dispatchFirstVisible() {
        if (firstVisible) {
            onFirstVisible();
            firstVisible = false;
        }
    }

    /**
     * This method is called when fragment is first visible
     */
    protected void onFirstVisible() {

    }

    /**
     * This method is called when clicking the back button.
     */
    public void addBackPressedListener(@NonNull BackPressedHelper.BackPressedListener listener) {
        BackPressedHelper.addBackPressedListener(this, listener);
    }

    public BaseActivity<?> getBaseAct() {
        return baseAct;
    }

    public BaseFragment<?> getBaseFrag() {
        return baseFrag;
    }

    public @Nullable VB getBinding() {
        return viewBinding;
    }

    public @NonNull VB requireBinding() {
        return viewBinding;
    }

    protected int layoutId() {
        return -1;
    }

    protected View layoutView() {
        return null;
    }

    protected VB inflateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return null;
    }
}
