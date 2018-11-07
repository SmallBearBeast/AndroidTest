package com.example.administrator.androidtest.Common.Bus;

import android.os.Bundle;
import android.support.annotation.Nullable;

public interface IBus {
    void send(String event, @Nullable Bundle extras);

    void sendSync(String event, @Nullable Bundle extras);

    void register(OnBusEventListener listener, String... events);

    void unregister(OnBusEventListener listener);

    interface OnBusEventListener {
        void onBusEvent(String event, @Nullable Bundle extras);
    }
}
