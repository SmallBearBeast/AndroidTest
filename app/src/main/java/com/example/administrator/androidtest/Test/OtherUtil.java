package com.example.administrator.androidtest.Test;

import android.view.MotionEvent;

public class OtherUtil {
    public static String toMotionEventName(MotionEvent ev) {
        String eventName = "";
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                eventName = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                eventName = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                eventName = "ACTION_UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                eventName = "ACTION_CANCEL";
                break;
            case MotionEvent.ACTION_SCROLL:
                eventName = "ACTION_SCROLL";
                break;
        }
        return eventName;
    }
}
