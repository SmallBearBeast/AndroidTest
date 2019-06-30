package com.example.administrator.androidtest.Base.ActAndFrag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.androidtest.Base.Component.IComponent;
import com.example.administrator.androidtest.Base.Component.ActComponent;
import com.example.administrator.androidtest.Base.Component.ViewSet;

import java.util.HashMap;
import java.util.Map;

public abstract class ComponentAct<C extends ActComponent, V extends ViewSet> extends BaseAct {
    public C mMainComponent;
    public V mViewSet;
    public View mContentView;
    protected Map<Class, IComponent> mComponentMap = new HashMap<>(8);

    protected C createComponent() {
        return null;
    }

    protected V createViewSet() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = LayoutInflater.from(this).inflate(layoutId(), null);
        setContentView(mContentView);
        init(savedInstanceState);
        initMainComponent();
    }

    private void initMainComponent() {
        mMainComponent = createComponent();
        mViewSet = createViewSet();
        if (mMainComponent != null && mViewSet != null) {
            mViewSet.attachView(mContentView);
            registerComponent(mMainComponent);
        }
    }

    protected <C extends IComponent> void registerComponent(C component) {
        if (component instanceof ActComponent) {
            ((ActComponent) component).attachActivity(this);
            ((ActComponent) component).attachViewSet(mViewSet);
        }
        if (component != null) {
            getLifecycle().addObserver(component);
            mComponentMap.put(component.getClass(), component);
        }
    }

    protected <C extends IComponent> C getcomponent(Class<C> clz) {
        if (mComponentMap.containsKey(clz)) {
            return (C) mComponentMap.get(clz);
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (IComponent component : mComponentMap.values()) {
            mComponentMap.remove(component.getClass());
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
