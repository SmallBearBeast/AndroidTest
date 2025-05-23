package com.bear.libcomponent.provider;

import android.content.Context;

import com.bear.libcomponent.component.ComponentActivity;

public interface IContextProvider {
    void attachContext(Context c);

    Context getContext();

    ComponentActivity<?> getActivity();
}
