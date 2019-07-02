package com.example.administrator.androidtest.Bus;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.util.SparseArray;
import com.example.administrator.androidtest.Common.Util.Core.MainThreadUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LocalBus implements IBus {

    private Map<OnBusEvent, Set<String>> mObservers;
    private SparseArray<List<String>> mStickIdMap;
    private SparseArray<List<String>> mUsedStickIdMap;
    private List<Event> mStickEventList;

    public LocalBus() {
        mObservers = new ConcurrentHashMap<>();
        mStickEventList = new ArrayList<>();
        mStickIdMap = new SparseArray<>();
        mUsedStickIdMap = new SparseArray<>();
    }

    @Override
    public void send(final Event event) {
        MainThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                sendSync(event);
            }
        });
    }

    @Override
    public void sendSync(final Event event) {
        for (Map.Entry<OnBusEvent, Set<String>> entry : mObservers.entrySet()) {
            if (entry.getValue().contains(event.mEventName)) {
                entry.getKey().onBusEvent(event.mEventName, event.mBundle);
            }
        }
    }

    @Override
    public void sendStick(Event event) {
        mStickEventList.remove(event);
        mStickEventList.add(event);
        for (int i = 0, size = mUsedStickIdMap.size(); i < size; i++) {
            List<String> usedList = mUsedStickIdMap.valueAt(i);
            if(usedList.contains(event.mEventName)){
                int stickId = mUsedStickIdMap.keyAt(i);
                if(event.mStickIdList.contains(stickId)) {
                    List<String> stickList = mStickIdMap.get(stickId);
                    if (!stickList.contains(event.mEventName)) {
                        stickList.add(event.mEventName);
                    }
                    usedList.remove(event.mEventName);
                }
            }
        }
    }

    @Override
    public void register(OnBusEvent busEvent) {
        List<String> stickList = mStickIdMap.get(busEvent.stickId());
        List<String> usedStickList = mUsedStickIdMap.get(busEvent.stickId());
        if(stickList != null && !stickList.isEmpty()) {
            for (Event event : mStickEventList) {
                if(event.mStickIdList != null && event.mStickIdList.contains(busEvent.stickId())){
                    if (stickList.contains(event.mEventName)) {
                        busEvent.onStickEvent(event.mEventName, event.mBundle);
                        if(usedStickList == null){
                            usedStickList = new ArrayList<>();
                            mUsedStickIdMap.put(busEvent.stickId(), usedStickList);
                        }
                        usedStickList.add(event.mEventName);
                        stickList.remove(event.mEventName);
                    }
                    event.mStickIdList.remove((Object)busEvent.stickId());
                }
            }
        }else {
            List<String> stickEvents = busEvent.stickEvents();
            if(stickEvents != null && busEvent.stickId() != -1) {
                mStickIdMap.put(busEvent.stickId(), busEvent.stickEvents());
            }
        }

        List<String> events = busEvent.events();
        if(events != null) {
            if (!mObservers.containsKey(busEvent)) {
                mObservers.put(busEvent, new HashSet<String>());
            }
            mObservers.get(busEvent).addAll(events);
        }
    }

    @Override
    public void unregister(OnBusEvent listener) {
        mObservers.remove(listener);
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            source.getLifecycle().removeObserver(this);
        }
    }
}
