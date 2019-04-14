package com.example.administrator.androidtest.Base.ActAndFrag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.androidtest.Base.Component.FragComponent;
import com.example.administrator.androidtest.Base.Component.IComponent;
import com.example.administrator.androidtest.Base.Component.ActComponent;
import com.example.administrator.androidtest.Base.Component.ViewSet;


import java.util.HashMap;
import java.util.Map;

public abstract class ComponentFrag <K extends FragComponent, T extends ViewSet> extends BaseFrag {
    public K mainComponent;
    public T viewSet;
    public View contentView;
    protected ComponentAct mComActivity;
    protected Map<Class, IComponent> mActComponentMap;
    protected Map<Class, IComponent> mFragComponentMap  = new HashMap<>(8);;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ComponentAct){
            mComActivity = (ComponentAct) context;
            mActComponentMap = mComActivity.componentMap;
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

    public  <C extends IComponent> void registerComponent(C component){
        if(component instanceof FragComponent){
            ((FragComponent)component).attachActivity(mComActivity);
            ((FragComponent)component).attachFragment(this);
        }
        if(component != null){
            mFragComponentMap.put(component.getClass(), component);
        }
    }

    public <C extends IComponent> C getFragComponent(Class<C> clz){
        if(mFragComponentMap.containsKey(clz)){
            return (C) mFragComponentMap.get(clz);
        }
        return null;
    }

    public <C extends IComponent> C getActComponent(Class<C> clz){
        if(mActComponentMap != null) {
            if (mActComponentMap.containsKey(clz)) {
                return (C) mActComponentMap.get(clz);
            }
        }
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (IComponent component : mFragComponentMap.values()) {
            component.onCreate();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        for (IComponent component : mFragComponentMap.values()) {
            component.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (IComponent component : mFragComponentMap.values()) {
            component.onStop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (IComponent component : mFragComponentMap.values()) {
            component.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (IComponent component : mFragComponentMap.values()) {
            component.onDestory();
            mFragComponentMap.remove(component.getClass());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (IComponent component : mFragComponentMap.values()) {
            component.onResume();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (IComponent component : mFragComponentMap.values()) {
            component.onActivityResult(requestCode, resultCode, data);
        }
    }
}
