package com.example.libframework.CoreUI;

import android.view.View;

public abstract class FragComponent extends BaseComponent<ComponentFrag> {
    protected ComponentAct mComActivity;

    void attachActivity(ComponentAct activity) {
        mComActivity = activity;
    }

    protected void onCreateView(View contentView) {

    }

    protected void onDestroyView(View contentView) {

    }
}
