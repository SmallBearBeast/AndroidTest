package com.bear.libcomponent.component;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

public class ViewComponent extends GroupComponent implements View.OnAttachStateChangeListener {

    private static final int INIT_COUNT = 32;

    private SparseArray<View> viewIdArray;

    // 组件持有的根组件的View。
    // 如果是Activity组件，持有的就是decorView。
    // 如果是Fragment组件，持有的就是Fragment的View。
    // 如果是View组件，持有的就是View本身。
    // 默认会继承Activity的View或者Fragment的View。
    private View contentView;

    public ViewComponent(View view) {
        onAttachView(view);
    }

    public ViewComponent(View view, Lifecycle lifecycle) {
        super(lifecycle);
        onAttachView(view);
    }

    protected void onAttachView(View view) {
        if (view != null && contentView != view) {
            contentView = view;
            contentView.addOnAttachStateChangeListener(this);
            viewIdArray = new SparseArray<>(INIT_COUNT);
            for (GroupComponent component : getComponentMap().values()) {
                if (component instanceof ViewComponent) {
                    ViewComponent viewComponent = (ViewComponent) component;
                    if (viewComponent.getContentView() == null) {
                        viewComponent.onAttachView(view);
                    }
                }
            }
        }
    }

    protected void onDetachView() {
        contentView.removeOnAttachStateChangeListener(this);
        contentView = null;
        if (viewIdArray != null) {
            viewIdArray.clear();
            viewIdArray = null;
        }
        for (GroupComponent component : getComponentMap().values()) {
            if (component instanceof ViewComponent) {
                ViewComponent viewComponent = (ViewComponent) component;
                viewComponent.onDetachView();
            }
        }
    }

    public View getContentView() {
        return contentView;
    }

    protected View findViewAndSetListener(View.OnClickListener listener, @IdRes int viewId) {
        View view = findViewById(viewId);
        setOnClickListener(listener, viewId);
        return view;
    }

    protected void setOnClickListener(View.OnClickListener listener, @IdRes int... viewIds) {
        for (int id : viewIds) {
            if (viewIdArray.get(id) != null) {
                viewIdArray.get(id).setOnClickListener(listener);
            } else {
                findViewById(id).setOnClickListener(listener);
            }
        }
    }

    protected <T extends View> T findViewById(@IdRes int viewId) {
        View view = viewIdArray.get(viewId);
        if (view == null) {
            view = contentView.findViewById(viewId);
            viewIdArray.put(viewId, view);
        }
        return (T) view;
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
        ViewComponent viewComponent = (ViewComponent) component;
        if (viewComponent.getContentView() == null) {
            viewComponent.onAttachView(getContentView());
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
