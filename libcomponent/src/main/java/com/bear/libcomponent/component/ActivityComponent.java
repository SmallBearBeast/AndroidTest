package com.bear.libcomponent.component;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.provider.IBackPressedProvider;
import com.bear.libcomponent.provider.IMenuProvider;

public class ActivityComponent extends ViewComponent implements IBackPressedProvider, IMenuProvider {

    private ComponentAct componentAct;

    public ActivityComponent(Lifecycle lifecycle) {
        super(null, lifecycle);
    }

    public void attachActivity(ComponentAct activity) {
        componentAct = activity;
    }

    @Override
    public ComponentAct getActivity() {
        return componentAct;
    }
}
