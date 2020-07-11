package com.example.libframework.Receiver;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.bear.libcomponent.IComponent;

public class HomeKeyReceiver extends BroadcastReceiver implements IComponent {
    private static final String REASON = "reason";
    private static final String HOME_KEY = "homekey";
    private HomeKeyListener mListener;
    private Context mContext;
    public HomeKeyReceiver(Context context, HomeKeyListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)){
            String reason = intent.getStringExtra(REASON);
            if(TextUtils.equals(reason, HOME_KEY)){
                if(mListener != null){
                    mListener.onHomeClick();
                }
            }
        }
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if(event == Lifecycle.Event.ON_DESTROY){
            mContext.unregisterReceiver(this);
            mListener = null;
        }else if(event == Lifecycle.Event.ON_CREATE){
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            mContext.registerReceiver(this, filter);
        }
    }

    public interface HomeKeyListener{
        void onHomeClick();
    }
}
