package com.example.libbase.Util;

import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewUtil extends AppInitUtil {
    public static void visible(View... views){
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void gone(View... views){
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    public static void invisible(View... views){
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void alpha(final View view, final float aplha){
        Runnable actionDown = new Runnable() {
            @Override
            public void run() {
                view.setAlpha(aplha);
            }
        };
        Runnable actionUp = new Runnable() {
            @Override
            public void run() {
                view.setAlpha(1);
            }
        };
        action(view, actionDown, actionUp, actionUp);
    }

    public static void tvTextColor(final TextView tv, @ColorRes final int srcColorId, @ColorRes final int dstColorId){
        tv.setTextColor(getColor(srcColorId));
        Runnable actionDown = new Runnable() {
            @Override
            public void run() {
                tv.setTextColor(getColor(dstColorId));
            }
        };
        Runnable actionUp = new Runnable() {
            @Override
            public void run() {
                tv.setTextColor(getColor(srcColorId));
            }
        };
        action(tv, actionDown, actionUp, actionUp);
    }

    public static void ivColorFilter(final ImageView iv, @ColorRes final int srcColorId, @ColorRes final int dstColorId){
        iv.setColorFilter(getColor(srcColorId));
        Runnable actionDown = new Runnable() {
            @Override
            public void run() {
                iv.setColorFilter(getColor(dstColorId));
            }
        };
        Runnable actionUp = new Runnable() {
            @Override
            public void run() {
                iv.setColorFilter(getColor(srcColorId));
            }
        };
        action(iv, actionDown, actionUp, actionUp);
    }


    public static void action(View view, final Runnable actionDown, final Runnable actionMove, final Runnable actionUp){
        view.setOnTouchListener(new View.OnTouchListener() {
            private RectF rectF;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(rectF == null || rectF.isEmpty()){
                            rectF = new RectF(0, 0, v.getWidth(), v.getHeight());
                        }
                        if(actionDown != null){
                            actionDown.run();
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if(!rectF.contains(event.getX(), event.getY())){
                            if(actionMove != null){
                                actionMove.run();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if(actionUp != null){
                            actionUp.run();
                        }
                        if(rectF.contains(event.getX(), event.getY())){
                            v.performClick();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private static int getColor(int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }

}
