package com.bear.libcomponent.component;

import java.util.Map;

public class ComponentContainer {

    private final RootComponent rootComponent = new RootComponent();

    public <C extends GroupComponent> void regComponent(C component, Object tag) {
        if (component == null) {
            return;
        }
        rootComponent.regComponent(component, tag);
    }

    public <C extends GroupComponent> C getComponent(Class<C> clz, Object tag) {
        return rootComponent.getComponent(clz, tag);
    }

    public Map<ComponentKey<?>, GroupComponent> getComponentMap() {
        return rootComponent.getComponentMap();
    }

    public <C extends IComponent> boolean contain(Class<C> clz, Object tag) {
        Map<ComponentKey<?>, GroupComponent> componentMap = getComponentMap();
        if (!componentMap.isEmpty()) {
            ComponentKey<?> componentKey = new ComponentKey<>(clz, tag);
            return componentMap.containsKey(componentKey);
        }
        return false;
    }

    private static class RootComponent extends GroupComponent {

    }
}
