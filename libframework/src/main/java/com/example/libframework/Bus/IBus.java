package com.example.libframework.Bus;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

public interface IBus extends GenericLifecycleObserver {
    void send(Event event);

    void sendSync(Event event);

    void sendStick(Event event);

    void register(OnBusEvent listener);

    void unregister(OnBusEvent listener);

    abstract class OnBusEvent {
        protected abstract void onBusEvent(String event, @Nullable Bundle extras);

        protected void onStickEvent(String event, @Nullable Bundle extras){}

        protected int stickId(){
            return -1;
        }

        protected List<String> events(){
            return null;
        }

        protected List<String> stickEvents(){
            return null;
        }
    }
}
