package com.example.administrator.androidtest.Share;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.example.administrator.androidtest.App;

public class HomeKeyReceiver extends BroadcastReceiver{
    private static final String REASON = "reason";
    private static final String HOME_KEY = "homekey";
    private HomeKeyListener mListener;
    public HomeKeyReceiver() {}

    public void onStart(HomeKeyListener listener){
        mListener = listener;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        App.getContext().registerReceiver(this, filter);
    }

    public void onStop(){
        App.getContext().unregisterReceiver(this);
        mListener = null;
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

    public interface HomeKeyListener{
        void onHomeClick();
    }
}
