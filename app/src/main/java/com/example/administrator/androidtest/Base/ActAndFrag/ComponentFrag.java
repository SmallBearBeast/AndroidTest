package com.example.administrator.androidtest.Base.ActAndFrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.Map;

public abstract class ComponentFrag <K extends Component, T extends ViewSet> extends BaseFrag {
    public K mainComponent;
    public T viewSet;
    public View contentView;
    protected ComponentAct mComActivity;
    protected Map<Class, Component> componentMap;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ComponentAct){
            mComActivity = (ComponentAct) context;
            componentMap = mComActivity.componentMap;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(contentView == null){
            contentView = inflater.inflate(layoutId(), container, false);
            init(savedInstanceState);
            initMainComponent();
        }
        return contentView;
    }

    private void initMainComponent(){
        if(mainComponent != null && viewSet != null){
            mainComponent.attachViewSet(viewSet);
            registerComponent(mainComponent);
        }
    }

    public  <C extends Component> void registerComponent(C component){
        if(component != null){
            component.attachActivity(mComActivity);
            componentMap.put(component.getClass(), component);
        }
    }

    public <C extends Component> C getcomponent(Class<C> clz){
        if(componentMap.containsKey(clz)){
            return (C) componentMap.get(clz);
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        for (Component component : componentMap.values()) {
            component.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (Component component : componentMap.values()) {
            component.onStop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (Component component : componentMap.values()) {
            component.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (Component component : componentMap.values()) {
            component.onDestory();
            componentMap.remove(component.getClass());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Component component : componentMap.values()) {
            component.onResume();
        }
    }
}
