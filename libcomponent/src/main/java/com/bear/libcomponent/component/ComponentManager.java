package com.bear.libcomponent.component;

import android.content.Context;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcomponent.provider.IBackPressedProvider;
import com.bear.libcomponent.provider.IMenuProvider;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

// TODO: 2023/12/19 能否统一成组件树的形式，不好实现
public class ComponentManager {

    private final ComponentContainer componentContainer = new ComponentContainer();

    public void regComponent(ComponentActivity activity, @NonNull ActivityComponent component) {
        regComponent(activity, component, null);
    }

    public void regComponent(ComponentActivity activity, @NonNull ActivityComponent component, @Nullable Object tag) {
        if (componentContainer.contain(component.getClass(), tag)) {
            throw new RuntimeException("Can not register component with same type and tag");
        }
        component.attachContext(activity);
        component.attachActivity(activity);
        component.onAttachView(activity.getDecorView());
        componentContainer.regComponent(component, tag);
    }

    public void regComponent(ComponentFragment fragment, FragmentComponent component) {
        regComponent(fragment, component, null);
    }

    public void regComponent(ComponentFragment fragment, @NonNull FragmentComponent component, @Nullable Object tag) {
        if (componentContainer.contain(component.getClass(), tag)) {
            throw new RuntimeException("Can not register component with same type and tag");
        }
        component.attachContext(fragment.getContext());
        if (fragment.getView() != null) {
            component.onAttachView(fragment.getView());
        }
        component.attachFragment(fragment);
        componentContainer.regComponent(component, tag);
    }

    public <C extends GroupComponent> C getComponent(Class<C> clz) {
        return getComponent(clz, null);
    }

    public <C extends GroupComponent> C getComponent(Class<C> clz, Object tag) {
        return componentContainer.getComponent(clz, tag);
    }

    void dispatchOnCreateView(ComponentFragment componentFrag, View contentView) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent && ((FragmentComponent) component).getFragment() == componentFrag) {
                    ((FragmentComponent) component).onAttachView(contentView);
                    ((FragmentComponent) component).onCreateView();
                }
            }
        }
    }

    void dispatchOnDestroyView(ComponentFragment componentFrag) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent && ((FragmentComponent) component).getFragment() == componentFrag) {
                    ((FragmentComponent) component).onDestroyView();
                    ((FragmentComponent) component).onDetachView();
                }
            }
        }
    }

    void dispatchOnAttach(ComponentFragment componentFrag, Context context) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent && ((FragmentComponent) component).getFragment() == componentFrag) {
                    ((FragmentComponent) component).attachContext(context);
                }
            }
        }
    }

    void dispatchOnDetach(ComponentFragment componentFrag) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent && ((FragmentComponent) component).getFragment() == componentFrag) {
                    ((BaseComponent) component).attachContext(null);
                }
            }
        }
    }

    void dispatchOnFirstVisible(ComponentFragment componentFrag) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent && ((FragmentComponent) component).getFragment() == componentFrag) {
                    ((FragmentComponent) component).onFirstVisible();
                }
            }
        }
    }

    void dispatchOnBackPressed() {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (GroupComponent component : componentMap.values()) {
                if (component instanceof IBackPressedProvider) {
                    ((IBackPressedProvider) component).onBackPressed();
                }
                if (component != null) {
                    component.foreach(com -> {
                        if (component instanceof IBackPressedProvider) {
                            ((IBackPressedProvider) component).onBackPressed();
                        }
                    });
                }
            }
        }
    }

    boolean dispatchOnCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        AtomicBoolean created = new AtomicBoolean(false);
        if (componentMap != null) {
            for (GroupComponent component : componentMap.values()) {
                if (component instanceof IMenuProvider) {
                    created.set(created.get() | ((IMenuProvider) component).onCreateOptionsMenu(menu, menuInflater));
                }
                if (component != null) {
                    component.foreach(com -> {
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
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        AtomicBoolean created = new AtomicBoolean(false);
        if (componentMap != null) {
            for (GroupComponent component : componentMap.values()) {
                if (component instanceof IMenuProvider) {
                    created.set(created.get() | ((IMenuProvider) component).onOptionsItemSelected(item));
                }
                if (component != null) {
                    component.foreach(com -> {
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
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (GroupComponent component : componentMap.values()) {
                if (component instanceof IMenuProvider) {
                    ((IMenuProvider) component).onCreateContextMenu(menu, v, menuInfo);
                }
                if (component != null) {
                    component.foreach(com -> {
                        if (component instanceof IMenuProvider) {
                            ((IMenuProvider) component).onCreateContextMenu(menu, v, menuInfo);
                        }
                    });
                }
            }
        }
    }

    boolean dispatchOnContextItemSelected(MenuItem item) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        AtomicBoolean created = new AtomicBoolean(false);
        if (componentMap != null) {
            for (GroupComponent component : componentMap.values()) {
                if (component instanceof IMenuProvider) {
                    created.set(created.get() | ((IMenuProvider) component).onContextItemSelected(item));
                }
                if (component != null) {
                    component.foreach(com -> {
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
