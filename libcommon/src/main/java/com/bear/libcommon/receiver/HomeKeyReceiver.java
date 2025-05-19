package com.bear.libcommon.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class HomeKeyReceiver extends BroadcastReceiver {
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
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
            String reason = intent.getStringExtra(REASON);
            if (TextUtils.equals(reason, HOME_KEY)) {
                if (mListener != null) {
                    mListener.onHomeClick();
                }
            }
        }
    }

    public interface HomeKeyListener {
        void onHomeClick();
    }
}
