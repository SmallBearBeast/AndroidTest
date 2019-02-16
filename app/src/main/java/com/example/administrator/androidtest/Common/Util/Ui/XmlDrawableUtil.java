package com.example.administrator.androidtest.Common.Util.Ui;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.example.administrator.androidtest.Common.Util.AppInitUtil;


/**
 * 1.设置selectorDrawable时候，normal属性必须放在最后，因为是按照添加顺序进行过滤
 * 2.设置selectorDrawable时候，要设置view.setClickable(true)属性
 * 3.一些需要上下文context的工具类可以继承AppInitUtil，统一初始化上下文Context
 *
 * 常用形状Drawable和selectorDrawable的封装
 * 用法:XmlDrawableUtil.selector(R.drawable.xxx_1, R.drawable.xxx_2).setView(mTv_2);
 */
public class XmlDrawableUtil extends AppInitUtil {

    public static int DRAWABLE_NONE = -1;
    public static int COLOR_NONE = -1;

    public static DrawableWapper selector(int normalId, int pressedId){
        return selector(normalId, pressedId, DRAWABLE_NONE);
    }

    public static DrawableWapper selector(int normalId, int pressedId, int checkedId){
        StateListDrawable drawable = new StateListDrawable();

        if(checkDrawableID(pressedId)){
            drawable.addState(new int[]{
                    android.R.attr.state_pressed
            }, getDrawable(pressedId));
        }

        if(checkDrawableID(checkedId)){
            drawable.addState(new int[]{
                    android.R.attr.state_checked
            }, getDrawable(checkedId));
        }


        if(checkDrawableID(normalId)){
            drawable.addState(new int[]{}, getDrawable(normalId));
        }

        return new DrawableWapper(drawable);
    }

    public static DrawableWapper selector(Drawable normalDrawable, Drawable pressedDrawable){
        return selector(normalDrawable, pressedDrawable, null);
    }

    public static DrawableWapper selector(Drawable normalDrawable, Drawable pressedDrawable, Drawable checkedDrawable){
        StateListDrawable drawable = new StateListDrawable();

        if(checkDrawable(pressedDrawable)){
            drawable.addState(new int[]{
                    android.R.attr.state_pressed
            }, pressedDrawable);
        }

        if(checkDrawable(checkedDrawable)){
            drawable.addState(new int[]{
                    android.R.attr.state_checked
            }, checkedDrawable);
        }

        if(checkDrawable(normalDrawable)){
            drawable.addState(new int[]{}, normalDrawable);
        }


        return new DrawableWapper(drawable);
    }


    private static Drawable getDrawable(int drawableId){
        return ContextCompat.getDrawable(sContext, drawableId);
    }

    private static int getColor(int colorId){
        return ContextCompat.getColor(sContext, colorId);
    }

    private static int getDp2Px(int dp){
        float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private static boolean checkDrawableID(int drawableId){
        return drawableId != DRAWABLE_NONE;
    }

    private static boolean checkDrawable(Object obj){
        return obj instanceof Drawable;
    }

    private static boolean checkColorId(int colorId){
        return colorId != COLOR_NONE;
    }

    public static DrawableWapper shapeCornerRect(int colorId, int radius){
        GradientDrawable drawable = new GradientDrawable();
        if(checkColorId(colorId)) {
            drawable.setColor(getColor(colorId));
        }
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(getDp2Px(radius));
        return new DrawableWapper(drawable);
    }

    public static DrawableWapper shapeStrokeRect(int colorId, int radius, int strokeColorId, int strokeWidth){
        GradientDrawable drawable = (GradientDrawable) shapeCornerRect(colorId, radius).mDrawable;
        if(checkDrawableID(strokeColorId)) {
            drawable.setStroke(getDp2Px(strokeWidth), getColor(strokeColorId));
        }
        return new DrawableWapper(drawable);
    }

    public static DrawableWapper shapeCircle(int colorId){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        if(checkColorId(colorId)) {
            drawable.setColor(getColor(colorId));
        }
        return new DrawableWapper(drawable);
    }

    public static DrawableWapper shapeStrokeCircle(int colorId, int strokeColorId, int strokeWidth){
        GradientDrawable drawable = (GradientDrawable) shapeCircle(colorId).mDrawable;
        if(checkDrawableID(strokeColorId)) {
            drawable.setStroke(getDp2Px(strokeWidth), getColor(strokeColorId));
        }
        return new DrawableWapper(drawable);
    }

    public static DrawableWapper selectorCornerRect(int normalColorId, int pressedColorId, int radius){
        Drawable normalDrawable = shapeCornerRect(normalColorId, radius).mDrawable;
        Drawable pressedDrawable = shapeCornerRect(pressedColorId, radius).mDrawable;
        return selector(normalDrawable, pressedDrawable);
    }

    public static DrawableWapper selectorCircle(int normalColorId, int pressedColorId){
        return selectorCircle(normalColorId, pressedColorId, COLOR_NONE);
    }

    public static DrawableWapper selectorCircle(int normalColorId, int pressedColorId, int checkedColorId){
        Drawable normalDrawable = shapeCircle(normalColorId).mDrawable;
        Drawable pressedDrawable = shapeCircle(pressedColorId).mDrawable;
        Drawable checkedDrawable = shapeCircle(checkedColorId).mDrawable;
        return selector(normalDrawable, pressedDrawable, checkedDrawable);
    }


    public static class DrawableWapper{
        Drawable mDrawable;

        public DrawableWapper(Drawable mDrawable) {
            this.mDrawable = mDrawable;
        }

        public void setView(View view){
            //最好使能下可点击属性
            view.setClickable(true);
            view.setFocusable(true);
            ViewCompat.setBackground(view, mDrawable);
        }

        public Drawable getDrawable(){
            return mDrawable;
        }
    }
}
