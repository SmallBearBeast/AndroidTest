package com.example.administrator.androidtest.Test.ComponentTest;

import android.os.Bundle;
import android.view.View;
import com.example.libframework.ActAndFrag.ComponentAct;
import com.example.libframework.Component.ActComponent;
import com.example.libframework.Component.ViewSet;


public class ComponentTestAct extends ComponentAct<ComponentTestAct.MainActComponent, ComponentTestAct.MainViewSet> {

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    static class MainActComponent extends ActComponent<MainViewSet> {

    }

    static class MainViewSet extends ViewSet {

    }

    @Override
    protected MainActComponent createComponent() {
        return new MainActComponent();
    }

    @Override
    protected MainViewSet createViewSet() {
        return new MainViewSet();
    }
}
