package com.bear.libcomponent.component;

import android.content.Context;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.bear.libcomponent.provider.IBackPressedProvider;
import com.bear.libcomponent.provider.IMenuProvider;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("unchecked")
public class ComponentManager {

    private final ComponentContainer componentContainer = new ComponentContainer();

    public <VB extends ViewBinding> void regComponent(ComponentActivity<VB> activity, @NonNull ActivityComponent<VB> component) {
        regComponent(activity, component, null);
    }

    public <VB extends ViewBinding> void regComponent(ComponentActivity<VB> activity, @NonNull ActivityComponent<VB> component, @Nullable Object tag) {
        if (componentContainer.contain(component.getClass(), tag)) {
            throw new RuntimeException("Can not register component with same type and tag");
        }
        component.attachContext(activity);
        component.attachActivity(activity);
        if (activity.getBinding() != null) {
            component.onAttachViewBinding(activity.getBinding());
        }
        componentContainer.regComponent(component, tag);
    }

    public <VB extends ViewBinding> void regComponent(ComponentFragment<VB> fragment, @NonNull FragmentComponent<VB> component) {
        regComponent(fragment, component, null);
    }

    public <VB extends ViewBinding> void regComponent(ComponentFragment<VB> fragment, @NonNull FragmentComponent<VB> component, @Nullable Object tag) {
        if (componentContainer.contain(component.getClass(), tag)) {
            throw new RuntimeException("Can not register component with same type and tag");
        }
        component.attachContext(fragment.getContext());
        component.attachFragment(fragment);
        if (fragment.getBinding() != null) {
            component.onAttachViewBinding(fragment.getBinding());
        }
        componentContainer.regComponent(component, tag);
    }

    public <C extends GroupComponent> C getComponent(Class<C> clz) {
        return getComponent(clz, null);
    }

    public <C extends GroupComponent> C getComponent(Class<C> clz, Object tag) {
        return componentContainer.getComponent(clz, tag);
    }

    void dispatchOnCreateView(ComponentFragment<?> componentFrag, ViewBinding binding) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent<?> && ((FragmentComponent<?>) component).getFragment() == componentFrag) {
                    ((FragmentComponent<ViewBinding>) component).onAttachViewBinding(binding);
                    ((FragmentComponent<?>) component).onCreateView();
                }
            }
        }
    }

    void dispatchOnDestroyView(ComponentFragment<?> componentFrag) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent<?> && ((FragmentComponent<?>) component).getFragment() == componentFrag) {
                    ((FragmentComponent<?>) component).onDestroyView();
                    ((FragmentComponent<?>) component).onDetachView();
                }
            }
        }
    }

    void dispatchOnAttach(ComponentFragment<?> componentFrag, Context context) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent<?> && ((FragmentComponent<?>) component).getFragment() == componentFrag) {
                    ((FragmentComponent<?>) component).attachContext(context);
                }
            }
        }
    }

    void dispatchOnDetach(ComponentFragment<?> componentFrag) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent<?> && ((FragmentComponent<?>) component).getFragment() == componentFrag) {
                    ((FragmentComponent<?>) component).attachContext(null);
                }
            }
        }
    }

    void dispatchOnFirstVisible(ComponentFragment<?> componentFrag) {
        Map<ComponentKey<?>, GroupComponent> componentMap = componentContainer.getComponentMap();
        if (componentMap != null) {
            for (IComponent component : componentMap.values()) {
                if (component instanceof FragmentComponent<?> && ((FragmentComponent<?>) component).getFragment() == componentFrag) {
                    ((FragmentComponent<?>) component).onFirstVisible();
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
