package com.example.libframework.CoreUI;

import android.view.View;

import androidx.annotation.CallSuper;

public abstract class FragComponent<T extends ViewSet> extends BaseComponent<ComponentFrag, T> {
    protected ComponentAct mComActivity;

    void attachActivity(ComponentAct activity) {
        mComActivity = activity;
    }

    protected void onCreateView(View contentView) {

    }

    @CallSuper
    protected void onDestroyView(View contentView) {
        super.onDestroy();
    }
}
