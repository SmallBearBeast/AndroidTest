package com.bear.libcomponent.component;

import androidx.lifecycle.Lifecycle;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public abstract class GroupComponent extends BaseComponent {
    private GroupComponent parentComponent;
    private final Map<ComponentKey<?>, GroupComponent> componentMap = new HashMap<>();

    public GroupComponent() {
        super();
    }

    public GroupComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    public <C extends GroupComponent> void regComponent(C component) {
        regComponent(component, null);
    }

    public <C extends GroupComponent> void regComponent(C component, Object tag) {
        ComponentKey<?> componentKey = new ComponentKey<>(component.getClass(), tag);
        if (componentMap.containsKey(componentKey)) {
            throw new RuntimeException("Can not register component with same type and tag");
        }
        if (component.getContext() == null) {
            component.attachContext(getContext());
        }
        if (component.getLifecycle() == null) {
            component.attachLifecycle(getLifecycle());
        }
        component.setLifecycleObserver((source, event) -> {
            if (event == Lifecycle.Event.ON_DESTROY) {
                componentMap.remove(componentKey);
            }
        });
        ((GroupComponent) component).parentComponent = this;
        componentMap.put(componentKey, component);
    }

    public <C extends GroupComponent> void unRegComponent(Class<C> clz) {
        unRegComponent(clz, null);
    }

    public <C extends GroupComponent> void unRegComponent(Class<C> clz, Object tag) {
        ComponentKey<?> targetKey = new ComponentKey<>(clz, tag);
        componentMap.remove(targetKey);
    }

    public <C extends GroupComponent> C getComponent(Class<C> clz) {
        return getComponent(clz, null);
    }

    public <C extends GroupComponent> C getComponent(Class<C> clz, Object tag) {
        ComponentKey<?> targetKey = new ComponentKey<>(clz, tag);
        GroupComponent targetComponent = travel(targetKey, null);
        if (targetComponent != null) {
            return (C) targetComponent;
        }
        GroupComponent pComponent = parentComponent;
        GroupComponent excludeComponent = this;
        while (targetComponent == null && pComponent != null) {
            targetComponent = pComponent.travel(targetKey, excludeComponent);
            excludeComponent = pComponent;
            pComponent = pComponent.parentComponent;
        }
        if (targetComponent != null) {
            return (C) targetComponent;
        }
        return null;
    }

    <C extends GroupComponent> C travel(ComponentKey<?> targetKey, GroupComponent excludeComponent) {
        if (componentMap.containsKey(targetKey)) {
            return (C) componentMap.get(targetKey);
        }
        for (GroupComponent component : componentMap.values()) {
            if (component == excludeComponent) {
                continue;
            }
            if (component != null) {
                GroupComponent targetComponent = component.travel(targetKey, excludeComponent);
                if (targetComponent != null) {
                    return (C) targetComponent;
                }
            }
        }
        return null;
    }

    void foreach(ForeachAction foreachAction) {
        for (GroupComponent component : componentMap.values()) {
            foreachAction.foreach(component);
            if (component != null) {
                component.foreach(foreachAction);
            }
        }
    }

    Map<ComponentKey<?>, GroupComponent> getComponentMap() {
        return componentMap;
    }

    interface ForeachAction {
        void foreach(GroupComponent component);
    }
}
