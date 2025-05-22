package com.bear.libcomponent.component;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.bear.libbase.fragment.BaseFragment;

public abstract class ComponentFragment<VB extends ViewBinding> extends BaseFragment<VB> {
    
    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponentManager().dispatchOnAttach(this, context);
    }

    @Nullable
    @Override
    @CallSuper
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);
        getComponentManager().dispatchOnCreateView(this, getBinding());
        return contentView;
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        getComponentManager().dispatchOnDestroyView(this);
    }

    @Override
    @CallSuper
    public void onDetach() {
        super.onDetach();
        getComponentManager().dispatchOnDetach(this);
    }

    @Override
    protected void onFirstVisible() {
        getComponentManager().dispatchOnFirstVisible(this);
    }

    public void regFragComponent(FragmentComponent component, Object tag) {
        getComponentManager().regComponent(this, component, tag);
    }

    public void regFragComponent(FragmentComponent component) {
        getComponentManager().regComponent(this, component);
    }

    public <C extends GroupComponent> C getComponent(Class<C> clz, Object tag) {
        return getComponentManager().getComponent(clz, tag);
    }

    public <C extends GroupComponent> C getComponent(Class<C> clz) {
        return getComponentManager().getComponent(clz);
    }
    
    private ComponentManager getComponentManager() {
        if (getActivity() instanceof ComponentActivity) {
            return ((ComponentActivity) getActivity()).getComponentManager();
        }
        throw new RuntimeException("getComponentManager return null");
    }

    @Override
    protected abstract VB inflateViewBinding(LayoutInflater inflater, ViewGroup container);
}
