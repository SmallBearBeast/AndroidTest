package com.example.administrator.androidtest.Base.Activity;

import android.os.Bundle;
import android.view.View;

import com.example.administrator.androidtest.Base.ActAndFrag.Component;
import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;
import com.example.administrator.androidtest.Base.ActAndFrag.ViewSet;

public class ComponentTestAct extends ComponentAct<ComponentTestAct.MainComponent, ComponentTestAct.MainViewSet> {

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mainComponent = new MainComponent();
        viewSet = new MainViewSet(null);
        mainComponent.attachViewSet(viewSet);
    }

    static class MainComponent extends Component<MainViewSet>{

    }

    static class MainViewSet extends ViewSet{

        public MainViewSet(View contentView) {
            super(contentView);
        }
    }
}
