package com.example.libframework.Component;

import com.example.libframework.ActAndFrag.ComponentAct;
import com.example.libframework.ActAndFrag.ComponentFrag;

public abstract class FragComponent<T extends ViewSet> extends BaseComponent<ComponentFrag, T> {
    protected ComponentAct mComActivity;

    public void attachActivity(ComponentAct activity) {
        mComActivity = activity;
    }
}
