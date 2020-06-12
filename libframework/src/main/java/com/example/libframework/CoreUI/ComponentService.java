package com.example.libframework.CoreUI;

import java.util.HashMap;
import java.util.Map;

public class ComponentService {
    private Map<ComponentKey, IComponent> mActComponentMap = new HashMap<>();
    private Map<ComponentKey, IComponent> mFragComponentMap = new HashMap<>();

    private static class SingleTon {
        private static final ComponentService INSTANCE = new ComponentService();
    }

    public ComponentService get() {
        return SingleTon.INSTANCE;
    }

    public  <C extends IComponent> void regComponent(ComponentAct activity, C component, Object tag) {
        if (component != null) {
            if (component instanceof ActComponent) {
                ((ActComponent) component).attachMain(activity);
                ((ActComponent) component).attachView(activity.getDecorView());
            }
            mActComponentMap.put(new ComponentKey(component.getClass(), tag), component);
            activity.getLifecycle().addObserver(component);
        }
    }

    public <C extends IComponent> void regComponent(ComponentAct activity, C component) {
        regComponent(activity, component, null);
    }

    public <C extends IComponent> C getComponent(Class<C> clz, Object tag) {
        ComponentKey componentKey = new ComponentKey(clz, tag);
        if (mActComponentMap.containsKey(componentKey)) {
            return (C) mActComponentMap.get(componentKey);
        }
        return null;
    }

    public <C extends IComponent> C getComponent(Class<C> clz) {
        return getComponent(clz, null);
    }


    public void regComponent(ComponentFrag fragment, IComponent component, Object tag) {
        if (component != null) {
            if (component instanceof FragComponent) {
                ((FragComponent) component).attachActivity(fragment.mComActivity);
                ((FragComponent) component).attachMain(fragment);
            }
            ComponentKey componentKey = new ComponentKey(component.getClass(), tag);
            mFragComponentMap.put(componentKey, component);
            mActComponentMap.put(componentKey, component);
            fragment.getLifecycle().addObserver(component);
        }
    }

    public void regComponent(ComponentFrag fragment, IComponent component) {
        regComponent(fragment, component, null);
    }
}
