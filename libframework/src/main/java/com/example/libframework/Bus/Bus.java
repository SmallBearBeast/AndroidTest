package com.example.libframework.Bus;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The Bus is used to communicate between different component.
 */
public class Bus {
    private Handler mHandler = new Handler();
    private Map<String, Event> mStickEventMap = new HashMap<>();
    private Set<EventCallback> mEventCallbackSet = new HashSet<>();

    public static Bus get() {
        return SingleTon.sBus;
    }

    private static class SingleTon {
        private static Bus sBus = new Bus();
    }

    private Bus() {

    }

    /**
     * Post a normal event
     * @param event Normal event
     */
    public void post(@NonNull final Event event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            postInternal(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    postInternal(event);
                }
            });
        }
    }

    private void postInternal(final Event event) {
        for (EventCallback callback : mEventCallbackSet) {
            if (callback.eventKeySet() == EventCallback.DEFAULT_KEY_SET || callback.eventKeySet().contains(event.eventKey)) {
                callback.onEvent(event);
            }
        }
    }

    /**
     * Post a sticky event and receive event right now when bus is registering callback
     * @param event Sticky event
     */
    public void postStick(@NonNull final Event event) {
        if (!mStickEventMap.containsKey(event.eventKey)) {
            mStickEventMap.put(event.eventKey, event);
        }
        post(event);
    }

    public void removeStick(@NonNull final Event event) {
        mStickEventMap.remove(event.eventKey);
    }

    public void register(@NonNull final EventCallback callback) {
        handleStickEvent(callback);
        mEventCallbackSet.add(callback);
    }

    /**
     * Should invoke this method when the onDestroy method is called.
     * @param callback EventCallback
     */
    public void unRegister(@NonNull final EventCallback callback) {
        mEventCallbackSet.remove(callback);
    }

    public void register(@NonNull LifecycleOwner owner, @NonNull EventCallback callback) {
        LifecycleBoundObserver observer = new LifecycleBoundObserver(owner, callback);
        owner.getLifecycle().addObserver(observer);
        handleStickEvent(callback);
        mEventCallbackSet.add(callback);
    }

    private void handleStickEvent(EventCallback callback) {
        Set<String> eventKeySet = callback.eventKeySet();
        if (!eventKeySet.isEmpty()) {
            for (String key : mStickEventMap.keySet()) {
                if (eventKeySet == EventCallback.DEFAULT_KEY_SET || eventKeySet.contains(key)) {
                    callback.onEvent(mStickEventMap.get(key));
                }
            }
        }
    }

    private class LifecycleBoundObserver implements LifecycleEventObserver {
        private LifecycleOwner mOwner;
        private EventCallback mCallback;

        LifecycleBoundObserver(LifecycleOwner owner, EventCallback callback) {
            mOwner = owner;
            mCallback = callback;
        }

        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            if (Lifecycle.Event.ON_DESTROY == event) {
                mOwner.getLifecycle().removeObserver(this);
                unRegister(mCallback);
            }
        }
    }
}
