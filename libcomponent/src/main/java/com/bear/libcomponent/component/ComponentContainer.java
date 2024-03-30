package com.bear.libcomponent.component;

import java.util.HashMap;
import java.util.Map;

public class ComponentContainer {
    private Map<ComponentKey, IComponent> componentMap = new HashMap<>();

    public void put(IComponent component, Object tag) {
        if (component == null) {
            return;
        }
        componentMap.put(new ComponentKey<>(component.getClass(), tag), component);
    }

    public <C extends IComponent> C get(Class<C> clz, Object tag) {
        IComponent targetComponent;
        if (!componentMap.isEmpty()) {
            ComponentKey componentKey = new ComponentKey<>(clz, tag);
            targetComponent = componentMap.get(componentKey);
            if (targetComponent != null) {
                return (C) targetComponent;
            }
            // Find target component from subComponent.
            for (IComponent component : componentMap.values()) {
                if (component instanceof ContainerComponent) {
                    ContainerComponent containerComponent = (ContainerComponent) component;
                    targetComponent = containerComponent.travel(componentKey, null);
                    if (targetComponent != null) {
                        return (C) targetComponent;
                    }
                }
            }
        }
        return null;
    }

    public void remove(IComponent component, Object tag) {
        if (!componentMap.isEmpty()) {
            ComponentKey componentKey = new ComponentKey<>(component.getClass(), tag);
            componentMap.remove(componentKey);
        }
    }

    public Map<ComponentKey, IComponent> getComponentMap() {
        return componentMap;
    }

    public <C extends IComponent> boolean contain(Class<C> clz, Object tag) {
        if (!componentMap.isEmpty()) {
            ComponentKey componentKey = new ComponentKey<>(clz, tag);
            return componentMap.containsKey(componentKey);
        }
        return false;
    }
}
