package com.example.libframework.CoreUI;

import java.util.HashMap;
import java.util.Map;

public abstract class ComponentAct extends BaseAct {
    protected Map<ComponentKey, IComponent> mComponentMap = new HashMap<>(8);

    protected <C extends IComponent> void regComponent(C component, Object tag) {
        if (component != null) {
            if (component instanceof ActComponent) {
                ((ActComponent) component).attachMain(this);
                ((ActComponent) component).attachView(getDecorView());
            }
            mComponentMap.put(new ComponentKey(component.getClass(), tag), component);
            getLifecycle().addObserver(component);
        }
    }

    protected <C extends IComponent> void regComponent(C component) {
        regComponent(component, null);
    }

    public <C extends IComponent> C getComponent(Class<C> clz, Object tag) {
        ComponentKey componentKey = new ComponentKey(clz, tag);
        if (mComponentMap.containsKey(componentKey)) {
            return (C) mComponentMap.get(componentKey);
        }
        return null;
    }

    public <C extends IComponent> C getComponent(Class<C> clz) {
        return getComponent(clz, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mComponentMap.clear();
    }
}
