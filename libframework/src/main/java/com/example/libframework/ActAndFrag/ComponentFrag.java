package com.example.libframework.ActAndFrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.libframework.Component.FragComponent;
import com.example.libframework.Component.IComponent;
import com.example.libframework.Component.ViewSet;

import java.util.HashMap;
import java.util.Map;

public abstract class ComponentFrag<C extends FragComponent, V extends ViewSet> extends BaseFrag {
    public C mMainComponent;
    public V mViewSet;
    public View mContentView;
    protected ComponentAct mComActivity;
    protected Map<Class, IComponent> mActComponentMap;
    protected Map<Class, IComponent> mFragComponentMap = new HashMap<>(8);

    protected C createComponent() {
        return null;
    }

    protected V createViewSet() {
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComponentAct) {
            mComActivity = (ComponentAct) context;
            mActComponentMap = mComActivity.mComponentMap;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(layoutId(), container, false);
            init(savedInstanceState);
            initMainComponent();
        }
        return mContentView;
    }

    private void initMainComponent() {
        mMainComponent = createComponent();
        mViewSet = createViewSet();
        if (mMainComponent != null && mViewSet != null) {
            mViewSet.attachView(mContentView);
            registerComponent(mMainComponent);
        }
    }

    public <C extends IComponent> void registerComponent(C component) {
        if (component instanceof FragComponent) {
            mMainComponent.attachViewSet(mViewSet);
            ((FragComponent) component).attachActivity(mComActivity);
            ((FragComponent) component).attachFragment(this);
        }
        if (component != null) {
            getLifecycle().addObserver(component);
            mFragComponentMap.put(component.getClass(), component);
        }
    }

    public <C extends IComponent> C getFragComponent(Class<C> clz) {
        if (mFragComponentMap.containsKey(clz)) {
            return (C) mFragComponentMap.get(clz);
        }
        return null;
    }

    public <C extends IComponent> C getActComponent(Class<C> clz) {
        if (mActComponentMap != null) {
            if (mActComponentMap.containsKey(clz)) {
                return (C) mActComponentMap.get(clz);
            }
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (IComponent component : mFragComponentMap.values()) {
            mFragComponentMap.remove(component.getClass());
        }
    }

}
