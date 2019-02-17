package com.example.administrator.androidtest.Base.ActAndFrag;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.androidtest.Common.Util.Core.PermissionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ComponentAct<K extends Component, T extends ViewSet> extends BaseAct {
    public K mainComponent;
    public T viewSet;
    public View contentView;
    protected Map<Class, Component> componentMap = new HashMap<>(8);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = LayoutInflater.from(this).inflate(layoutId(), null);
        setContentView(contentView);
        init(savedInstanceState);
        initMainComponent();
        PermissionUtil.requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
        }, mActivity, new PermissionListener() {
            @Override
            public void onPermissionRequest(List<String> permissionSuccessArray, List<String> permissionFailArray) {

            }
        });
    }

    protected void initMainComponent(){
        if(mainComponent != null && viewSet != null){
            mainComponent.attachViewSet(viewSet);
            registerComponent(mainComponent);
        }
    }

    protected <C extends Component> void registerComponent(C component){
        if(component != null){
            component.attachActivity(this);
            componentMap.put(component.getClass(), component);
        }
    }

    protected <C extends Component> C getcomponent(Class<C> clz){
        if(componentMap.containsKey(clz)){
            return (C) componentMap.get(clz);
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (Component component : componentMap.values()) {
            component.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (Component component : componentMap.values()) {
            component.onStop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (Component component : componentMap.values()) {
            component.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Component component : componentMap.values()) {
            component.onDestory();
            componentMap.remove(component.getClass());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (Component component : componentMap.values()) {
            component.onResume();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Component component : componentMap.values()) {
            component.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
