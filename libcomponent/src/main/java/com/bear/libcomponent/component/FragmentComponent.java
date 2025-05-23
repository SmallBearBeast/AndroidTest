package com.bear.libcomponent.component;

import android.os.Bundle;

import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;

import com.bear.libcomponent.provider.IBackPressedProvider;
import com.bear.libcomponent.provider.IMenuProvider;

public class FragmentComponent<VB extends ViewBinding> extends ViewComponent<VB> implements IBackPressedProvider, IMenuProvider {

    private ComponentFragment<?> componentFrag;

    public FragmentComponent(Lifecycle lifecycle) {
        super(null, lifecycle);
    }

    void attachFragment(ComponentFragment<?> fragment) {
        componentFrag = fragment;
    }

    public ComponentFragment<?> getFragment() {
        return componentFrag;
    }

    protected void onCreateView() {

    }

    protected void onDestroyView() {

    }

    protected void onFirstVisible() {

    }

    public Bundle getArguments() {
        return componentFrag.getArguments();
    }
}
