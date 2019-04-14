package com.example.administrator.androidtest.Bus;

public class BusProvider {

    private static IBus sBus;
    private static IBus sLocalBus;

    public static IBus getLocal() {
        if (sLocalBus == null) {
            synchronized (BusProvider.class) {
                if (sLocalBus == null) {
                    sLocalBus = new LocalBus();
                }
            }
        }
        return sLocalBus;
    }
}
