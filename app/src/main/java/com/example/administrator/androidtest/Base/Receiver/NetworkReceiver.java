package com.example.administrator.androidtest.Base.Receiver;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.example.administrator.androidtest.Base.Component.IComponent;
import com.example.libbase.Util.AppUtil;
import com.example.libbase.Util.EnvUtil;
import com.example.libbase.Util.NetWorkUtil;

public class NetworkReceiver extends BroadcastReceiver implements IComponent {

    private NetworkChangeListener mNetworkChangeListener;

    public NetworkReceiver(NetworkChangeListener networkChangeListener) {
        mNetworkChangeListener = networkChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(mNetworkChangeListener != null){
            mNetworkChangeListener.isConnected(NetWorkUtil.isConnected());
        }
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if(event == Lifecycle.Event.ON_DESTROY){
            EnvUtil.getApp().unregisterReceiver(this);
        }else if(event == Lifecycle.Event.ON_CREATE){
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            EnvUtil.getApp().registerReceiver(this, filter);
        }
    }

    public interface NetworkChangeListener{
        void isConnected(boolean connect);
    }
}
