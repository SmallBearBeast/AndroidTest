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
    protected ComponentAct mComActivity;
    private Map<Class, IComponent> mComponentMap = new HashMap<>(8);
    private Map<Class, IComponent> mTempComponentMap = new HashMap<>(8);

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

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onComponentAttach();
        for (IComponent component : mTempComponentMap.values()) {
            getLifecycle().addObserver(component);
        }
    }

    @Nullable
    @Override
    @CallSuper
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);
        onComponentCreateView();
        return contentView;
    }

    public void regComponent(IComponent component) {
        if (component != null) {
            mComponentMap.put(component.getClass(), component);
            mTempComponentMap.put(component.getClass(), component);
        }
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

    private void onComponentCreateView() {
        for (IComponent component : mTempComponentMap.values()) {
            if (component instanceof FragComponent) {
                ((FragComponent)component).attachView(mContentView);
                ((FragComponent)component).onCreateView(mContentView);
            }
        }
    }

    private void onComponentDestroyView() {
        for (IComponent component : mTempComponentMap.values()) {
            if (component instanceof FragComponent) {
                ((FragComponent)component).attachView(null);
                ((FragComponent)component).onDestroyView(mContentView);
            }
        }
    }

    public <C extends IComponent> C getComponent(Class<C> clz) {
        if (mComponentMap.containsKey(clz)) {
            return (C) mComponentMap.get(clz);
        }
        return null;
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
        for (IComponent component : mTempComponentMap.values()) {
            mComponentMap.remove(component.getClass());
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
