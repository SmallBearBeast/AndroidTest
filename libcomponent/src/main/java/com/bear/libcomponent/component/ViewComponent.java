package com.bear.libcomponent.component;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;

@SuppressWarnings("unchecked")
public class ViewComponent<VB extends ViewBinding> extends GroupComponent implements View.OnAttachStateChangeListener {

    // 组件持有的根组件的View。
    // 如果是Activity组件，持有的就是decorView。
    // 如果是Fragment组件，持有的就是Fragment的View。
    // 如果是View组件，持有的就是View本身。
    // 默认会继承Activity的View或者Fragment的View。
    private VB viewBinding;

    public ViewComponent(VB binding) {
        onAttachViewBinding(binding);
    }

    public ViewComponent(VB binding, Lifecycle lifecycle) {
        super(lifecycle);
        if (binding != null) {
            onAttachViewBinding(binding);
        }
    }

    @CallSuper
    protected void onAttachViewBinding(@NonNull VB binding) {
        viewBinding = binding;
        binding.getRoot().addOnAttachStateChangeListener(this);
        for (GroupComponent component : getComponentMap().values()) {
            if (component instanceof ViewComponent<?>) {
                // 需要转成ViewBinding，而不是VB，有可能子Component的VB类型与父Component类型不一致，会类型转化错误。
                ViewComponent<ViewBinding> viewComponent = (ViewComponent<ViewBinding>) component;
                // 子Component没有VB对象，复用父Component的VB。
                if (viewComponent.getRoot() == null) {
                    viewComponent.onAttachViewBinding(binding);
                }
            }
        }
    }

    @CallSuper
    protected void onDetachView() {
        if (viewBinding != null) {
            viewBinding.getRoot().removeOnAttachStateChangeListener(this);
        }
        for (GroupComponent component : getComponentMap().values()) {
            if (component instanceof ViewComponent<?>) {
                ViewComponent<?> viewComponent = (ViewComponent<?>) component;
                viewComponent.onDetachView();
            }
        }
    }

    public View getRoot() {
        return viewBinding != null ? viewBinding.getRoot() : null;
    }

    public VB getBinding() {
        return viewBinding;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull View v) {

    }

    @Override
    public void onViewDetachedFromWindow(@NonNull View v) {

    }

    @Override
    public <C extends GroupComponent> void regComponent(C component, Object tag) {
        if (!(component instanceof ViewComponent)) {
            throw new RuntimeException("Only register ViewComponent as a child component");
        }
        super.regComponent(component, tag);
        // 需要转成ViewBinding，而不是VB，有可能子Component的VB类型与父Component类型不一致，会类型转化错误。
        ViewComponent<ViewBinding> viewComponent = (ViewComponent<ViewBinding>) component;
        // 子Component没有VB对象，复用父Component的VB。
        if (viewComponent.getRoot() == null && viewBinding != null) {
            viewComponent.onAttachViewBinding(viewBinding);
        }
    }

    @Override
    public <C extends GroupComponent> C getComponent(Class<C> clz, Object tag) {
        C component = super.getComponent(clz, tag);
        if (component == null) {
            return null;
        }
        if (!(component instanceof ViewComponent)) {
            throw new RuntimeException("Only get ViewComponent as a child component");
        }
        return component;
    }
}
