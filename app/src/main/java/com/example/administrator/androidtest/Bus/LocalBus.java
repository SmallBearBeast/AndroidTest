package com.example.administrator.androidtest.Bus;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.androidtest.Common.Util.Core.MainThreadUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LocalBus implements IBus {

    private Map<OnBusEventListener, Set<String>> mObservers;

    public LocalBus() {
        mObservers = new ConcurrentHashMap<>();
    }

    @Override
    public void send(final String event, @Nullable final Bundle extras) {
        MainThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                sendSync(event, extras);
            }
        });
    }

    @Override
    public void sendSync(String event, @Nullable Bundle extras) {
        for (Map.Entry<OnBusEventListener, Set<String>> entry : mObservers.entrySet()) {
            if (entry.getValue().contains(event)) {
                entry.getKey().onBusEvent(event, extras);
            }
        }
    }

    @Override
    public void register(OnBusEventListener listener, String... events) {
        if (!mObservers.containsKey(listener)) {
            mObservers.put(listener, new HashSet<String>());
        }
        for (String event : events) {
            mObservers.get(listener).add(event);
        }
    }

    @Override
    public void unregister(OnBusEventListener listener) {
        mObservers.remove(listener);
    }
}
