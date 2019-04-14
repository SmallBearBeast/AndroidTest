package com.example.administrator.androidtest.Base.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.example.administrator.androidtest.Base.Component.IComponent;
import com.example.administrator.androidtest.Common.Util.Core.AppUtil;
import com.example.administrator.androidtest.Common.Util.Core.NetWorkUtil;

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
    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        AppUtil.getApp().registerReceiver(this, filter);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onDestory(){
        AppUtil.getApp().unregisterReceiver(this);
    }

    public interface NetworkChangeListener{
        void isConnected(boolean connect);
    }
}
