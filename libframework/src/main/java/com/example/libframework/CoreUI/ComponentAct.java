package com.example.libframework.CoreUI;

import java.util.HashMap;
import java.util.Map;

public abstract class ComponentAct extends BaseAct {
    protected Map<Class, IComponent> mComponentMap = new HashMap<>(8);

    protected <C extends IComponent> void regComponent(C component) {
        if (component instanceof ActComponent) {
            ((ActComponent) component).attachMain(this);
            ((ActComponent) component).attachView(getDecorView());
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
        mComponentMap.clear();
    }
}
