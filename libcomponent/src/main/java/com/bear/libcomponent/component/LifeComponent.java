package com.bear.libcomponent.component;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

public abstract class LifeComponent implements IComponent {

    protected final String TAG = getClass().getSimpleName();

    private Lifecycle lifecycle;

    private LifecycleEventObserver lifecycleObserver;

    public LifeComponent() {

    }

    public LifeComponent(Lifecycle lifecycle) {
        attachLifecycle(lifecycle);
    }

    public void attachLifecycle(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);
    }

    public void setLifecycleObserver(LifecycleEventObserver observer) {
        lifecycleObserver = observer;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            onCreate();
        } else if (event == Lifecycle.Event.ON_START) {
            onStart();
        } else if (event == Lifecycle.Event.ON_RESUME) {
            onResume();
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            onPause();
        } else if (event == Lifecycle.Event.ON_STOP) {
            onStop();
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            onDestroy();
            lifecycle.removeObserver(this);
        }
        if (lifecycleObserver != null) {
            lifecycleObserver.onStateChanged(source, event);
            lifecycleObserver = null;
        }
    }

    protected void onCreate() {

    }

    protected void onStart() {

    }

    protected void onResume() {

    }

    protected void onPause() {

    }

    protected void onStop() {

    }

    protected void onDestroy() {

    }

    public Lifecycle getLifecycle() {
        return lifecycle;
    }
}
