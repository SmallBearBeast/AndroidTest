package com.example.libframework.Bus;

public class Bus {
    private static IBus sLocalBus;

    public static IBus get() {
        if (sLocalBus == null) {
            synchronized (Bus.class) {
                if (sLocalBus == null) {
                    sLocalBus = new LocalBus();
                }
            }
        }
        return sLocalBus;
    }
}
