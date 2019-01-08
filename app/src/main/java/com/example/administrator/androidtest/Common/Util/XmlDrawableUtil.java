package com.example.administrator.androidtest.Common.Util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class XmlDrawableUtil {

    public static int DRAWABLE_NONE = -1;

    private static Context sContext;

    public static void init(Context context){
        sContext = context;
    }

    public static Drawable selector(int normalId, int pressedId){
        return selector(normalId, pressedId, DRAWABLE_NONE);
    }

    public static Drawable selector(int normalId, int pressedId, int checkedId){
        StateListDrawable drawable = new StateListDrawable();
        if(checkDrawableID(normalId)){
            drawable.addState(new int[]{
                    android.R.attr.state_enabled, android.R.attr.state_active
            }, getDrawable(normalId));
        }

        if(checkDrawableID(pressedId)){
            drawable.addState(new int[]{
                    android.R.attr.state_pressed
            }, getDrawable(pressedId));
        }

        if(checkDrawableID(checkedId)){
            drawable.addState(new int[]{
                    android.R.attr.state_checked, android.R.attr.state_selected
            }, getDrawable(checkedId));
        }
        return drawable;
    }

    public static Drawable selector(Drawable normalDrawable, Drawable pressedDrawable){
        return selector(normalDrawable, pressedDrawable, null);
    }

    public static Drawable selector(Drawable normalDrawable, Drawable pressedDrawable, Drawable checkedDrawable){
        StateListDrawable drawable = new StateListDrawable();
        if(checkDrawable(normalDrawable)){
            drawable.addState(new int[]{
                    android.R.attr.state_enabled, android.R.attr.state_active
            }, normalDrawable);
        }

        if(checkDrawable(pressedDrawable)){
            drawable.addState(new int[]{
                    android.R.attr.state_pressed
            }, pressedDrawable);
        }

        if(checkDrawable(checkedDrawable)){
            drawable.addState(new int[]{
                    android.R.attr.state_checked, android.R.attr.state_selected
            }, checkedDrawable);
        }
        return drawable;
    }


    private static Drawable getDrawable(int drawableId){
        return ContextCompat.getDrawable(sContext, drawableId);
    }

    private static boolean checkDrawableID(int drawableId){
        return drawableId != DRAWABLE_NONE;
    }

    private static boolean checkDrawable(Object obj){
        return obj instanceof Drawable;
    }

    public static Drawable shapeCornerRect(int color, int radius){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    public static Drawable shapeStrokeRect(int color, int radius, int strokeColor, int strokeWidth){
        GradientDrawable drawable = (GradientDrawable) shapeCornerRect(color, radius);
        drawable.setStroke(strokeWidth, strokeColor);
        return drawable;
    }

    public static Drawable shapeCircle(View view, int color){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setSize(view.getWidth(), view.getHeight());
        drawable.setColor(color);
        return drawable;
    }

    public static Drawable shapeStrokeCircle(View view, int color, int strokeColor, int strokeWidth){
        GradientDrawable drawable = (GradientDrawable) shapeCircle(view, color);
        drawable.setStroke(strokeWidth, strokeColor);
        return drawable;
    }

    /**
     * 1.基本形状(圆形，圆角，边框)
     * 2.可以组合(基本形状之间的组合，以及和selector之间的组合)
     */

    public static Drawable selectorCornerRect(int normalColor, int pressedColor, int radius){
        Drawable normalDrawable = shapeCornerRect(normalColor, radius);
        Drawable pressedDrawable = shapeCornerRect(pressedColor, radius);
        return selector(normalDrawable, pressedDrawable);
    }

    public static Drawable selectorCircle(int normalColor, int pressedColor, View view){
        Drawable normalDrawable = shapeCircle(view, normalColor);
        Drawable pressedDrawable = shapeCircle(view, pressedColor);
        return selector(normalDrawable, pressedDrawable);
    }
}
