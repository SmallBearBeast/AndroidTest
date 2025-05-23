package com.bear.libcomponent.component;

import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;

import com.bear.libcomponent.provider.IBackPressedProvider;
import com.bear.libcomponent.provider.IMenuProvider;

public class ActivityComponent<VB extends ViewBinding> extends ViewComponent<VB> implements IBackPressedProvider, IMenuProvider {

    private ComponentActivity<?> componentAct;

    public ActivityComponent(Lifecycle lifecycle) {
        super(null, lifecycle);
    }

    public void attachActivity(ComponentActivity<?> activity) {
        componentAct = activity;
    }

    @Override
    public ComponentActivity<?> getActivity() {
        return componentAct;
    }
}
