package com.example.administrator.androidtest.Bus;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Event {
    public List<Integer> mStickIdList;
    public String mEventName;
    public Bundle mBundle;

    public Event(String eventName, Bundle bundle, int ... stickIds) {
        mEventName = eventName;
        mBundle = bundle;
        mStickIdList = new ArrayList<>();
        for (int stickId : stickIds) {
            mStickIdList.add(stickId);
        }
    }

    public Event(String eventName, Bundle bundle) {
        mEventName = eventName;
        mBundle = bundle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return mEventName != null ? mEventName.equals(event.mEventName) : event.mEventName == null;
    }

    @Override
    public int hashCode() {
        return mEventName != null ? mEventName.hashCode() : 0;
    }
}
