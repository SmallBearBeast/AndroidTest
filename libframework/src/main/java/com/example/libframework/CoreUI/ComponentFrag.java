package com.example.libframework.CoreUI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

public abstract class ComponentFrag extends BaseFrag {
    public ComponentAct mComActivity;
    private Map<ComponentKey, IComponent> mComponentMap = new HashMap<>(8);
    private Map<ComponentKey, IComponent> mTempComponentMap = new HashMap<>(8);

    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComponentAct) {
            mComActivity = (ComponentAct) context;
            mComponentMap = mComActivity.mComponentMap;
            mComponentMap.putAll(mTempComponentMap);
            onComponentAttach();
        }
    }

    @Nullable
    @Override
    @CallSuper
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);
        onComponentCreateView(contentView);
        return contentView;
    }

    public void regComponent(IComponent component, Object tag) {
        if (component != null) {
            if (component instanceof FragComponent) {
                ((FragComponent)component).attachActivity(mComActivity);
                ((FragComponent)component).attachMain(this);
            }
            ComponentKey componentKey = new ComponentKey(component.getClass(), tag);
            mComponentMap.put(componentKey, component);
            mTempComponentMap.put(componentKey, component);
            getLifecycle().addObserver(component);
        }
    }

    public void regComponent(IComponent component) {
        regComponent(component, null);
    }

    private void onComponentAttach() {
        for (IComponent component : mTempComponentMap.values()) {
            if (component instanceof FragComponent) {
                ((FragComponent)component).attachActivity(mComActivity);
                ((FragComponent)component).attachMain(this);
            }
        }
    }

    private void onComponentDetach() {
        for (IComponent component : mTempComponentMap.values()) {
            if (component instanceof FragComponent) {
                ((FragComponent)component).attachActivity(null);
                ((FragComponent)component).attachMain(null);
            }
        }
    }

    private void onComponentCreateView(View contentView) {
        for (IComponent component : mTempComponentMap.values()) {
            if (component instanceof FragComponent) {
                ((FragComponent)component).attachView(contentView);
                ((FragComponent)component).onCreateView(contentView);
            }
        }
    }

    private void onComponentDestroyView() {
        for (IComponent component : mTempComponentMap.values()) {
            if (component instanceof FragComponent) {
                ((FragComponent)component).attachView(null);
                ((FragComponent)component).onDestroyView(getView());
            }
        }
    }

    public <C extends IComponent> C getComponent(Class<C> clz, Object tag) {
        ComponentKey componentKey = new ComponentKey(clz, tag);
        if (mComponentMap.containsKey(componentKey)) {
            return (C) mComponentMap.get(componentKey);
        }
        return null;
    }

    public <C extends IComponent> C getComponent(Class<C> clz) {
        return getComponent(clz, null);
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        onComponentDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        for (ComponentKey componentKey : mTempComponentMap.keySet()) {
            mComponentMap.remove(componentKey);
        }
        mTempComponentMap.clear();
    }

    @Override
    @CallSuper
    public void onDetach() {
        super.onDetach();
        onComponentDetach();
        mComActivity = null;
    }
}
