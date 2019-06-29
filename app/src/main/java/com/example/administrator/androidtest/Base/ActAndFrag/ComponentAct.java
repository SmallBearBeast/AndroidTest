package com.example.administrator.androidtest.Base.ActAndFrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.androidtest.Base.Component.IComponent;
import com.example.administrator.androidtest.Base.Component.ActComponent;
import com.example.administrator.androidtest.Base.Component.ViewSet;
import java.util.HashMap;
import java.util.Map;

public abstract class ComponentAct<K extends ActComponent, T extends ViewSet> extends BaseAct {
    public K mainComponent;
    public T viewSet;
    public View contentView;
    protected Map<Class, IComponent> componentMap = new HashMap<>(8);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = LayoutInflater.from(this).inflate(layoutId(), null);
        setContentView(contentView);
        init(savedInstanceState);
        initMainComponent();
        for (IComponent component : componentMap.values()) {
            component.onCreate();
        }
    }

    private void initMainComponent(){
        if(mainComponent != null && viewSet != null){
            mainComponent.attachViewSet(viewSet);
            registerComponent(mainComponent);
        }
    }

    protected <C extends IComponent> void registerComponent(C component){
        if(component instanceof ActComponent){
            ((ActComponent)component).attachActivity(this);
        }
        if(component != null){
            componentMap.put(component.getClass(), component);
        }
    }

    protected <C extends IComponent> C getcomponent(Class<C> clz){
        if(componentMap.containsKey(clz)){
            return (C) componentMap.get(clz);
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (IComponent component : componentMap.values()) {
            component.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (IComponent component : componentMap.values()) {
            component.onStop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (IComponent component : componentMap.values()) {
            component.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (IComponent component : componentMap.values()) {
            component.onDestory();
            componentMap.remove(component.getClass());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (IComponent component : componentMap.values()) {
            component.onResume();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (IComponent component : componentMap.values()) {
            component.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
