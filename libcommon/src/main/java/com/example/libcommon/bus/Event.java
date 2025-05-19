package com.example.libcommon.bus;

import java.util.Objects;

public class Event {
    public String eventKey;
    public Object data;

    public Event(String key, Object value) {
        eventKey = key;
        data = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventKey, event.eventKey);
    }

    @Override
    public int hashCode() {
        return eventKey != null ? eventKey.hashCode() : 0;
    }

    public <T> T getData(Class<T> clz) {
        if (data != null && data.getClass() == clz) {
            return (T) data;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventKey='" + eventKey + '\'' +
                ", data=" + data +
                '}';
    }
}
