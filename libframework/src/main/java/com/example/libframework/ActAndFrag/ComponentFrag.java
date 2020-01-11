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
    protected ComponentAct mComActivity;
    protected Map<Class, IComponent> mComponentMap = new HashMap<>(8);
    protected Map<Class, IComponent> mTempComponentMap = new HashMap<>(8);

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
            mComponentMap = mComActivity.mComponentMap;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView != null) {
            initMainComponent();
        }
        return contentView;
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
            ((FragComponent) component).attachViewSet(mViewSet);
            ((FragComponent) component).attachActivity(mComActivity);
            ((FragComponent) component).attachMain(this);
        }
        if (component != null) {
            getLifecycle().addObserver(component);
            mComponentMap.put(component.getClass(), component);
            mTempComponentMap.put(component.getClass(), component);
        }
    }

    public <C extends IComponent> C getComponent(Class<C> clz) {
        if (mComponentMap.containsKey(clz)) {
            return (C) mComponentMap.get(clz);
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (IComponent component : mTempComponentMap.values()) {
            mComponentMap.remove(component.getClass());
        }
        mTempComponentMap.clear();
        mTempComponentMap = null;
    }

}
