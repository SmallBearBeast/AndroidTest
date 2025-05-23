package com.bear.libcommon.bus;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public abstract class EventCallback {
    static final Set<String> DEFAULT_KEY_SET = new HashSet<>();

    protected abstract void onEvent(@NonNull Event event);

    /**
     * The key set which callback can solve.
     * @return DEFAULT_KEY_SET means that all events can be accepted.
     */
    protected @NonNull Set<String> eventKeySet() {
        return DEFAULT_KEY_SET;
    }
}
