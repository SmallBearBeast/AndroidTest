package com.example.libcommon.receiver;

import androidx.annotation.RequiresPermission;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

public class NetReceiver extends BroadcastReceiver {

    private static NetChangeListener sNetChangeListener;
    private static Application sApp;

    private NetReceiver() {

    }

    public static void init(Application app, NetChangeListener netChangeListener) {
        sApp = app;
        sNetChangeListener = netChangeListener;
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        sApp.registerReceiver(new NetReceiver(), filter);
        sNetChangeListener = netChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (sNetChangeListener != null) {
                sNetChangeListener.onConnected(isConnected());
            }
        }
    }

    public interface NetChangeListener {
        void onConnected(boolean connect);
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    private NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) sApp.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return null;
        return cm.getActiveNetworkInfo();
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    private boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
