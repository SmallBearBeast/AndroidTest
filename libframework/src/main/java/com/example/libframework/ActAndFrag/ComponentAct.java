package com.example.libframework.ActAndFrag;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.libframework.Component.ActComponent;
import com.example.libframework.Component.IComponent;
import com.example.libframework.Component.ViewSet;

import java.util.HashMap;
import java.util.Map;

public abstract class ComponentAct<C extends ActComponent, V extends ViewSet> extends BaseAct {
    protected C mMainComponent;
    protected V mViewSet;
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
        initMainComponent();
    }

    private void initMainComponent() {
        mMainComponent = createComponent();
        mViewSet = createViewSet();
        if (mMainComponent != null && mViewSet != null) {
            mViewSet.attachView(getDecorView());
            registerComponent(mMainComponent);
        }
    }

    protected <C extends IComponent> void registerComponent(C component) {
        if (component instanceof ActComponent) {
            ((ActComponent) component).attachMain(this);
            ((ActComponent) component).attachViewSet(mViewSet);
        }
        if (component != null) {
            getLifecycle().addObserver(component);
            mComponentMap.put(component.getClass(), component);
        }
    }

    protected <C extends IComponent> C getComponent(Class<C> clz) {
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
