package com.example.administrator.androidtest.Test.ComponentTest;

import android.os.Bundle;
import android.view.View;

import com.example.administrator.androidtest.Base.Component.ActComponent;
import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;
import com.example.administrator.androidtest.Base.Component.ViewSet;

public class ComponentTestAct extends ComponentAct<ComponentTestAct.MainActComponent, ComponentTestAct.MainViewSet> {

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mainComponent = new MainActComponent();
        viewSet = new MainViewSet(null);
        mainComponent.attachViewSet(viewSet);
    }

    static class MainActComponent extends ActComponent<MainViewSet> {

    }

    static class MainViewSet extends ViewSet{

        public MainViewSet(View contentView) {
            super(contentView);
        }
    }
}
