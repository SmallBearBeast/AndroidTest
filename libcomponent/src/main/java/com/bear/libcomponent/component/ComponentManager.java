package com.bear.libcomponent.component;

import android.content.Context;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.bear.libcomponent.provider.IBackPressedProvider;
import com.bear.libcomponent.provider.IMenuProvider;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

// TODO: 2023/12/19 能否统一成组件树的形式，不好实现
public class ComponentManager {

    private final ComponentContainer componentContainer = new ComponentContainer();

    public <C extends IComponent> void regActComponent(ComponentAct activity, @NonNull C component) {
        regActComponent(activity, component, null);
    }

    public <C extends IComponent> void regActComponent(ComponentAct activity, @NonNull C component, @Nullable Object tag) {
        if (componentContainer.contain(component.getClass(), tag)) {
            throw new RuntimeException("Can not register component with same type and tag");
        }
        if (component instanceof BaseComponent) {
            ((BaseComponent) component).attachComponentContainer(componentContainer);
            ((BaseComponent) component).attachContext(activity);
            ((BaseComponent) component).attachView(activity.getDecorView());
        }
        if (component instanceof ActivityComponent) {
            ((ActivityComponent) component).attachActivity(activity);
        }
        componentContainer.put(component, tag);
        activity.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                component.onStateChanged(source, event);
                if (event == Lifecycle.Event.ON_DESTROY) {
                    source.getLifecycle().removeObserver(this);
                    componentContainer.remove(component, tag);
                }
            }
        });
    }

    public <C extends IComponent> void regFragComponent(ComponentFrag fragment, C component) {
        regFragComponent(fragment, fragment.getLifecycle(), component, null);
    }

    public <C extends IComponent> void regFragComponent(ComponentFrag fragment, C component, Object tag) {
        regFragComponent(fragment, fragment.getLifecycle(), component, tag);
    }

    public <C extends IComponent> void regFragViewComponent(ComponentFrag fragment, C component) {
        regFragComponent(fragment, fragment.getViewLifecycleOwner().getLifecycle(), component, null);
    }

    public <C extends IComponent> void regFragViewComponent(ComponentFrag fragment, C component, Object tag) {
        regFragComponent(fragment, fragment.getViewLifecycleOwner().getLifecycle(), component, tag);
    }

    private <C extends IComponent> void regFragComponent(ComponentFrag fragment, Lifecycle lifecycle, @NonNull C component, @Nullable Object tag) {
        if (componentContainer.contain(component.getClass(), tag)) {
            throw new RuntimeException("Can not register component with same type and tag");
        }
        if (component instanceof BaseComponent) {
            ((BaseComponent) component).attachComponentContainer(componentContainer);
            ((BaseComponent) component).attachContext(fragment.getContext());
            if (fragment.getView() != null) {
                ((BaseComponent) component).attachView(fragment.getView());
            }
        }
        if (component instanceof FragmentComponent) {
            ((FragmentComponent) component).attachFragment(fragment);
        }
        componentContainer.put(component, tag);
        lifecycle.addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                component.onStateChanged(source, event);
                if (event == Lifecycle.Event.ON_DESTROY) {
                    source.getLifecycle().removeObserver(this);
                    componentContainer.remove(component, tag);
                }
            }
        });
    }

    public <C extends IComponent> C getComponent(Class<C> clz) {
        return getComponent(clz, null);
    }

    public <C extends IComponent> C getComponent(Class<C> clz, Object tag) {
        return componentContainer.get(clz, tag);
    }

    void dispatchOnCreateView(ComponentFrag componentFrag, View contentView) {
        Map<ComponentKey, IComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent && ((FragmentComponent) component).getFragment() == componentFrag) {
                    ((FragmentComponent) component).attachView(contentView);
                    ((FragmentComponent) component).onCreateView();
                }
            }
        }
    }

    void dispatchOnDestroyView(ComponentFrag componentFrag) {
        Map<ComponentKey, IComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent && ((FragmentComponent) component).getFragment() == componentFrag) {
                    ((FragmentComponent) component).onDestroyView();
                    ((FragmentComponent) component).attachView(null);
                }
            }
        }
    }

    void dispatchOnAttach(ComponentFrag componentFrag, Context context) {
        Map<ComponentKey, IComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent && ((FragmentComponent) component).getFragment() == componentFrag) {
                    ((FragmentComponent) component).attachContext(context);
                }
            }
        }
    }

    void dispatchOnDetach(ComponentFrag componentFrag) {
        Map<ComponentKey, IComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent && ((FragmentComponent) component).getFragment() == componentFrag) {
                    ((BaseComponent) component).attachContext(null);
                }
            }
        }
    }

    void dispatchOnFirstVisible(ComponentFrag componentFrag) {
        Map<ComponentKey, IComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent && ((FragmentComponent) component).getFragment() == componentFrag) {
                    ((FragmentComponent) component).onFirstVisible();
                }
            }
        }
    }

    void dispatchOnBackPressed() {
        Map<ComponentKey, IComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof IBackPressedProvider) {
                    ((IBackPressedProvider) component).onBackPressed();
                }
                if (component instanceof ContainerComponent) {
                    ((ContainerComponent) component).foreach(com -> {
                        if (component instanceof IBackPressedProvider) {
                            ((IBackPressedProvider) component).onBackPressed();
                        }
                    });
                }
            }
        }
    }

    boolean dispatchOnCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Map<ComponentKey, IComponent> componentMap = componentContainer.getComponentMap();
        AtomicBoolean created = new AtomicBoolean(false);
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof IMenuProvider) {
                    created.set(created.get() | ((IMenuProvider) component).onCreateOptionsMenu(menu, menuInflater));
                }
                if (component instanceof ContainerComponent) {
                    ((ContainerComponent) component).foreach(com -> {
                        if (component instanceof IMenuProvider) {
                            created.set(created.get() | ((IMenuProvider) component).onCreateOptionsMenu(menu, menuInflater));
                        }
                    });
                }
            }
        }
        return created.get();
    }

    boolean dispatchOnOptionsItemSelected(MenuItem item) {
        Map<ComponentKey, IComponent> componentMap = componentContainer.getComponentMap();
        AtomicBoolean created = new AtomicBoolean(false);
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof IMenuProvider) {
                    created.set(created.get() | ((IMenuProvider) component).onOptionsItemSelected(item));
                }
                if (component instanceof ContainerComponent) {
                    ((ContainerComponent) component).foreach(com -> {
                        if (component instanceof IMenuProvider) {
                            created.set(created.get() | ((IMenuProvider) component).onOptionsItemSelected(item));
                        }
                    });
                }
            }
        }
        return created.get();
    }

    void dispatchOnCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Map<ComponentKey, IComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof IMenuProvider) {
                    ((IMenuProvider) component).onCreateContextMenu(menu, v, menuInfo);
                }
                if (component instanceof ContainerComponent) {
                    ((ContainerComponent) component).foreach(com -> {
                        if (component instanceof IMenuProvider) {
                            ((IMenuProvider) component).onCreateContextMenu(menu, v, menuInfo);
                        }
                    });
                }
            }
        }
    }

    boolean dispatchOnContextItemSelected(MenuItem item) {
        Map<ComponentKey, IComponent> componentMap = componentContainer.getComponentMap();
        AtomicBoolean created = new AtomicBoolean(false);
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof IMenuProvider) {
                    created.set(created.get() | ((IMenuProvider) component).onContextItemSelected(item));
                }
                if (component instanceof ContainerComponent) {
                    ((ContainerComponent) component).foreach(com -> {
                        if (component instanceof IMenuProvider) {
                            created.set(created.get() | ((IMenuProvider) component).onContextItemSelected(item));
                        }
                    });
                }
            }
        }
        return created.get();
    }
}
