package com.bear.libcommon;

import android.view.View;

public abstract class OnProtectClickListener implements View.OnClickListener {
    private long lastClickTs;
    private long diffTime = 500;

    public OnProtectClickListener() {

    }

    public OnProtectClickListener(long diffTime) {
        this.diffTime = diffTime;
    }

    @Override
    public void onClick(View v) {
        if (allowClick()) {
            onProtectClick(v);
        }
    }

    public abstract void onProtectClick(View v);

    private boolean allowClick() {
        long currentTs = System.currentTimeMillis();
        if (currentTs - lastClickTs > diffTime) {
            lastClickTs = currentTs;
            return true;
        }
        return false;
    }
}
