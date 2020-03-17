package com.example.libframework.CoreUI;

public abstract class FragComponent extends BaseComponent<ComponentFrag> {
    protected ComponentAct mComActivity;

    void attachActivity(ComponentAct activity) {
        mComActivity = activity;
    }

    protected void onCreateView() {

    }

    protected void onDestroyView() {

    }

    protected void onFirstVisible() {

    }
}
