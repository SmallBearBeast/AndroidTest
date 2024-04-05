package com.bear.libcomponent.component;

import android.os.Bundle;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.provider.IBackPressedProvider;
import com.bear.libcomponent.provider.IMenuProvider;

public class FragmentComponent extends ViewComponent implements IBackPressedProvider, IMenuProvider {

    private ComponentFrag componentFrag;

    public FragmentComponent(Lifecycle lifecycle) {
        super(null, lifecycle);
    }

    void attachFragment(ComponentFrag fragment) {
        componentFrag = fragment;
    }

    public ComponentFrag getFragment() {
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
