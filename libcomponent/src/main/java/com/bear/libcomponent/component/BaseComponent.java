package com.bear.libcomponent.component;

import android.content.Context;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.provider.IContextProvider;

public abstract class BaseComponent extends LifeComponent implements IContextProvider {

    private Context context;

    public BaseComponent() {
        super();
    }

    public BaseComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    public void attachContext(Context c) {
        context = c;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public ComponentAct getActivity() {
        if (context instanceof ComponentAct) {
            return (ComponentAct) context;
        }
        return null;
    }
}
