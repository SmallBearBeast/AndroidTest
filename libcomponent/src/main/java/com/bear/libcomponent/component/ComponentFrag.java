package com.bear.libcomponent.component;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcomponent.base.BaseFrag;

public abstract class ComponentFrag extends BaseFrag {
    
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
        getComponentManager().dispatchOnCreateView(this, contentView);
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

    public void regFragComponent(IComponent component, Object tag) {
        getComponentManager().regFragComponent(this, component, tag);
    }

    public void regFragComponent(IComponent component) {
        getComponentManager().regFragComponent(this, component);
    }

    public void regFragViewComponent(IComponent component, Object tag) {
        getComponentManager().regFragViewComponent(this, component, tag);
    }

    public void regFragViewComponent(IComponent component) {
        getComponentManager().regFragViewComponent(this, component);
    }

    public <C extends IComponent> C getComponent(Class<C> clz, Object tag) {
        return getComponentManager().getComponent(clz, tag);
    }

    public <C extends IComponent> C getComponent(Class<C> clz) {
        return getComponentManager().getComponent(clz);
    }
    
    private ComponentManager getComponentManager() {
        if (getActivity() instanceof ComponentAct) {
            return ((ComponentAct) getActivity()).getComponentManager();
        }
        throw new RuntimeException("getComponentManager return null");
    }
}
