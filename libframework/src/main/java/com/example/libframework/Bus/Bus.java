package com.example.libframework.Bus;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Bus is used to communicate between different component.
 */
public class Bus {
    private static Bus sBus;
    private Handler mHandler = new Handler();
    private Map<String, Event> mStickEventMap = new HashMap<>();
    private List<EventCallback> mObserverList = new ArrayList<>();

    public static Bus get() {
        if (sBus == null) {
            synchronized (Bus.class) {
                if (sBus == null) {
                    sBus = new Bus();
                }
            }
        }
        return sBus;
    }

    private Bus() {

    }

    /**
     * Post a normal event
     * @param event Normal event
     */
    public void post(final Event event) {
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
        for (EventCallback onBusEvent : mObserverList) {
            if (onBusEvent.eventKeySet() == EventCallback.DEFAULT_KEY_SET || onBusEvent.eventKeySet().contains(event.eventKey)) {
                onBusEvent.onEvent(event);
            }
        }
    }

    /**
     * Post a sticky event and receive event right now when bus is registering callback
     * @param event Sticky event
     */
    public void postStick(final Event event) {
        if (!mStickEventMap.containsKey(event.eventKey)) {
            mStickEventMap.put(event.eventKey, event);
        }
        post(event);
    }

    public void removeStick(final Event event) {
        mStickEventMap.remove(event.eventKey);
    }

    public void register(final EventCallback callback) {
        Set<String> eventKeySet = callback.eventKeySet();
        if (!eventKeySet.isEmpty()) {
            for (String key : mStickEventMap.keySet()) {
                if (eventKeySet == EventCallback.DEFAULT_KEY_SET || eventKeySet.contains(key)) {
                    callback.onEvent(mStickEventMap.get(key));
                }
            }
        }
        mObserverList.add(callback);
    }

    /**
     * Should invoke this method when the onDestroy method is called.
     * @param callback EventCallback
     */
    public void unRegister(final EventCallback callback) {
        mObserverList.remove(callback);
    }
}
