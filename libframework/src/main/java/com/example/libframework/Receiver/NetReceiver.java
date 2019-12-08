package com.example.libframework.Receiver;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import com.example.libbase.Util.EnvUtil;
import com.example.libbase.Util.NetWorkUtil;
import com.example.libframework.Component.IComponent;

public class NetReceiver extends BroadcastReceiver implements IComponent {

    private NetChangeListener mNetChangeListener;

    public NetReceiver(NetChangeListener netChangeListener) {
        mNetChangeListener = netChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if(mNetChangeListener != null){
                mNetChangeListener.onConnected(NetWorkUtil.isConnected());
            }
        }
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if(event == Lifecycle.Event.ON_DESTROY){
            EnvUtil.getApp().unregisterReceiver(this);
        }else if(event == Lifecycle.Event.ON_CREATE){
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            EnvUtil.getApp().registerReceiver(this, filter);
        }
    }

    public interface NetChangeListener {
        void onConnected(boolean connect);
    }
}
