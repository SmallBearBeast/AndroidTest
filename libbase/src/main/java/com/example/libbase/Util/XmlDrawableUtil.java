package com.example.libbase.Util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;


/**
 * 1.设置selectorDrawable时候，normal属性必须放在最后，因为是按照添加顺序进行过滤
 * 2.设置selectorDrawable时候，要设置view.setClickable(true)属性
 * 3.一些需要上下文context的工具类可以继承AppInitUtil，统一初始化上下文Context
 * <p>
 * 常用形状Drawable和selectorDrawable的封装
 * 用法:XmlDrawableUtil.selector(R.drawable.xxx_1, R.drawable.xxx_2).setView(mTv_2);
 */
public class XmlDrawableUtil extends AppInitUtil {
    public static int DRAWABLE_NONE = -1;
    public static int COLOR_NONE = -1;

    public static GDWrapper cornerRect(int colorId, int radius) {
        return new GDWrapper().cornerRect(colorId, radius);
    }

    public static GDWrapper strokeRect(int colorId, int radius, int strokeColorId, int strokeWidth) {
        return new GDWrapper().strokeRect(colorId, radius, strokeColorId, strokeWidth);
    }

    public static GDWrapper circle(int colorId) {
        return new GDWrapper().circle(colorId);
    }

    public static GDWrapper strokeCircle(int colorId, int strokeColorId, int strokeWidth) {
        return new GDWrapper().strokeCircle(colorId, strokeColorId, strokeWidth);
    }

    public static GDWrapper gradient(int[] colorIds, GradientDrawable.Orientation orientation) {
        return new GDWrapper().gradient(colorIds, orientation);
    }

    public static GDWrapper alpha(float alpha, int colorId) {
        return new GDWrapper().alpha(alpha, colorId);
    }

    public static SLWrapper selector(int normalResId, int pressedResId) {
        return new SLWrapper().selector(normalResId, pressedResId, DRAWABLE_NONE);
    }

    public static SLWrapper selector(int normalResId, int pressedResId, int checkedResId) {
        return new SLWrapper().selector(normalResId, pressedResId, checkedResId);
    }

    public static SLWrapper selector(Drawable normalDrawable, Drawable pressedDrawable) {
        return new SLWrapper().selector(normalDrawable, pressedDrawable, null);
    }

    public static SLWrapper selector(Drawable normalDrawable, Drawable pressedDrawable, Drawable checkedDrawable) {
        return new SLWrapper().selector(normalDrawable, pressedDrawable, checkedDrawable);
    }

    public static SLWrapper slCRect(int normalColorId, int pressedColorId, int radius) {
        Drawable normalDrawable = cornerRect(normalColorId, radius).mDrawable;
        Drawable pressedDrawable = cornerRect(pressedColorId, radius).mDrawable;
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slCircle(int normalColorId, int pressedColorId) {
        return slCircle(normalColorId, pressedColorId, COLOR_NONE);
    }

    public static SLWrapper slCircle(int normalColorId, int pressedColorId, int checkedColorId) {
        Drawable normalDrawable = circle(normalColorId).mDrawable;
        Drawable pressedDrawable = circle(pressedColorId).mDrawable;
        Drawable checkedDrawable = circle(checkedColorId).mDrawable;
        return selector(normalDrawable, pressedDrawable, checkedDrawable);
    }

    public static SLWrapper slGradient(int[] normalColorIds, int[] pressedColorIds, GradientDrawable.Orientation orientation) {
        Drawable normalDrawable = gradient(normalColorIds, orientation).mDrawable;
        Drawable pressedDrawable = gradient(pressedColorIds, orientation).mDrawable;
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slAlpha(float normalAlpha, float pressAlpha, int colorId) {
        Drawable normalDrawable = alpha(normalAlpha, colorId).mDrawable;
        Drawable pressedDrawable = alpha(pressAlpha, colorId).mDrawable;
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slAlphaCRect(float normalAlpha, float pressAlpha, int colorId, int radius) {
        Drawable normalDrawable = alpha(normalAlpha, colorId).cornerRect(colorId, radius).mDrawable;
        Drawable pressedDrawable = alpha(pressAlpha, colorId).cornerRect(colorId, radius).mDrawable;
        return selector(normalDrawable, pressedDrawable);
    }

    public static class SLWrapper{
        StateListDrawable mDrawable;

        SLWrapper() {
            mDrawable = new StateListDrawable();
        }

        public void setView(View view) {
            ViewCompat.setBackground(view, mDrawable);
        }

        private SLWrapper selector(int normalResId, int pressedResId, int checkedResId) {
            if (checkDrawableID(pressedResId)) {
                mDrawable.addState(new int[]{
                        android.R.attr.state_pressed
                }, getDrawable(pressedResId));
            }
            if (checkDrawableID(checkedResId)) {
                mDrawable.addState(new int[]{
                        android.R.attr.state_checked
                }, getDrawable(checkedResId));
            }
            if (checkDrawableID(normalResId)) {
                mDrawable.addState(new int[]{}, getDrawable(normalResId));
            }
            return this;
        }

        private SLWrapper selector(Drawable normalDrawable, Drawable pressedDrawable, Drawable checkedDrawable) {
            if (checkDrawable(pressedDrawable)) {
                mDrawable.addState(new int[]{
                        android.R.attr.state_pressed
                }, pressedDrawable);
            }
            if (checkDrawable(checkedDrawable)) {
                mDrawable.addState(new int[]{
                        android.R.attr.state_checked
                }, checkedDrawable);
            }
            if (checkDrawable(normalDrawable)) {
                mDrawable.addState(new int[]{}, normalDrawable);
            }
            return this;
        }
    }

    public static class GDWrapper{
        GradientDrawable mDrawable;

        GDWrapper() {
            mDrawable = new GradientDrawable();
        }

        public void setView(View view) {
            ViewCompat.setBackground(view, mDrawable);
        }

        private GDWrapper circle(int colorId) {
            mDrawable.setShape(GradientDrawable.OVAL);
            if (checkColorId(colorId)) {
                mDrawable.setColor(getColor(colorId));
            }
            return this;
        }

        private GDWrapper cornerRect(int colorId, int radius) {
            if (checkColorId(colorId)) {
                mDrawable.setColor(getColor(colorId));
            }
            mDrawable.setShape(GradientDrawable.RECTANGLE);
            mDrawable.setCornerRadius(getDp2Px(radius));
            return this;
        }

        private GDWrapper gradient(int[] colorIds, GradientDrawable.Orientation orientation) {
            if (colorIds.length > 1) {
                int[] colors = new int[colorIds.length];
                for (int i = 0; i < colorIds.length; i++) {
                    if (checkColorId(colorIds[i])) {
                        colors[i] = getColor(colorIds[i]);
                    }
                }
                mDrawable.setColors(colors);
                mDrawable.setOrientation(orientation);
            }
            return this;
        }

        private GDWrapper alpha(float alpha, int colorId) {
            if (alpha >= 0 && alpha <= 1.0f) {
                mDrawable.setAlpha((int) (alpha * 0xff));
                if (checkColorId(colorId)) {
                    mDrawable.setColor(getColor(colorId));
                }
            }
            return this;
        }

        private GDWrapper stroke(int strokeColorId, int strokeWidth){
            if (checkDrawableID(strokeColorId)) {
                mDrawable.setStroke(getDp2Px(strokeWidth), getColor(strokeColorId));
            }
            return this;
        }

        private GDWrapper strokeRect(int colorId, int radius, int strokeColorId, int strokeWidth) {
            return cornerRect(colorId, radius).stroke(strokeColorId, strokeWidth);
        }

        private GDWrapper strokeCircle(int colorId, int strokeColorId, int strokeWidth) {
            return circle(colorId).stroke(strokeColorId, strokeWidth);
        }
    }

    private static Drawable getDrawable(int drawableId) {
        return ContextCompat.getDrawable(getContext(), drawableId);
    }

    private static int getColor(int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }

    private static int getDp2Px(int dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private static boolean checkDrawableID(int drawableId) {
        return drawableId != DRAWABLE_NONE;
    }

    private static boolean checkDrawable(Object obj) {
        return obj instanceof Drawable;
    }

    private static boolean checkColorId(int colorId) {
        return colorId != COLOR_NONE;
    }

}
