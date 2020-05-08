package com.example.libbase.Util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.view.View;


/**
 * This is the encapsulation of commonly used ShapeDrawable and SelectorDrawable.
 * Usage:
 * 1.XmlDrawableUtil.selector(R.drawable.xxx_1, R.drawable.xxx_2).setView(tv);
 * 2.XmlDrawableUtil.rect(R.color.white, 5).setView(tv);
 * Note:
 * 1.The color must be a reference resource.
 * 2.The unit of length must be dp.
 */
public class XmlDrawableUtil extends AppInitUtil {
    private static int DRAWABLE_NONE = -1;
    private static int COLOR_NONE = -1;

    public static GDWrapper rect(@ColorRes int colorId, float... radii) {
        return new GDWrapper().color(colorId).rect(radii);
    }

    public static GDWrapper circle(@ColorRes int colorId) {
        return new GDWrapper().color(colorId).circle();
    }

    public static GDWrapper strokeRect(@ColorRes int colorId, @ColorRes int strokeColorId, float strokeWidth, float... radii) {
        return new GDWrapper().color(colorId).rect(radii).stroke(strokeColorId, strokeWidth);
    }

    public static GDWrapper strokeCircle(@ColorRes int colorId, @ColorRes int strokeColorId, float strokeWidth) {
        return new GDWrapper().color(colorId).circle().stroke(strokeColorId, strokeWidth);
    }

    public static GDWrapper gradientRect(int[] colorIds, GradientDrawable.Orientation orientation, float... radii) {
        return new GDWrapper().gradient(colorIds, orientation).rect(radii);
    }

    public static GDWrapper gradientCircle(int[] colorIds, GradientDrawable.Orientation orientation) {
        return new GDWrapper().gradient(colorIds, orientation).circle();
    }

    public static GDWrapper alphaRect(float alpha, @ColorRes int colorId, float... radii) {
        return new GDWrapper().color(colorId).alpha(alpha).rect(radii);
    }

    public static GDWrapper alphaCircle(float alpha, @ColorRes int colorId) {
        return new GDWrapper().color(colorId).alpha(alpha).circle();
    }

    public static SLWrapper selector(@DrawableRes int normalResId, @DrawableRes int pressedResId) {
        return new SLWrapper().selector(normalResId, pressedResId, DRAWABLE_NONE);
    }

    public static SLWrapper selector(@DrawableRes int normalResId, @DrawableRes int pressedResId, @DrawableRes int checkedResId) {
        return new SLWrapper().selector(normalResId, pressedResId, checkedResId);
    }

    public static SLWrapper selector(Drawable normalDrawable, Drawable pressedDrawable) {
        return new SLWrapper().selector(normalDrawable, pressedDrawable, null);
    }

    public static SLWrapper selector(Drawable normalDrawable, Drawable pressedDrawable, Drawable checkedDrawable) {
        return new SLWrapper().selector(normalDrawable, pressedDrawable, checkedDrawable);
    }

    public static SLWrapper slRect(@ColorRes int normalColorId, @ColorRes int pressedColorId, float... radii) {
        Drawable normalDrawable = rect(normalColorId, radii).getDrawable();
        Drawable pressedDrawable = rect(pressedColorId, radii).getDrawable();
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slRect(@ColorRes int normalColorId, @ColorRes int pressedColorId, @ColorRes int checkedColorId, float... radii) {
        Drawable normalDrawable = rect(normalColorId, radii).getDrawable();
        Drawable pressedDrawable = rect(pressedColorId, radii).getDrawable();
        Drawable checkedDrawable = rect(checkedColorId, radii).getDrawable();
        return selector(normalDrawable, pressedDrawable, checkedDrawable);
    }

    public static SLWrapper slCircle(@ColorRes int normalColorId, @ColorRes int pressedColorId) {
        Drawable normalDrawable = circle(normalColorId).getDrawable();
        Drawable pressedDrawable = circle(pressedColorId).getDrawable();
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slCircle(@ColorRes int normalColorId, @ColorRes int pressedColorId, @ColorRes int checkedColorId) {
        Drawable normalDrawable = circle(normalColorId).getDrawable();
        Drawable pressedDrawable = circle(pressedColorId).getDrawable();
        Drawable checkedDrawable = circle(checkedColorId).getDrawable();
        return selector(normalDrawable, pressedDrawable, checkedDrawable);
    }

    public static SLWrapper slGradientRect(int[] normalColorIds, int[] pressedColorIds, GradientDrawable.Orientation orientation, float... radii) {
        Drawable normalDrawable = gradientRect(normalColorIds, orientation, radii).getDrawable();
        Drawable pressedDrawable = gradientRect(pressedColorIds, orientation, radii).getDrawable();
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slGradientCircle(int[] normalColorIds, int[] pressedColorIds, GradientDrawable.Orientation orientation) {
        Drawable normalDrawable = gradientCircle(normalColorIds, orientation).getDrawable();
        Drawable pressedDrawable = gradientCircle(pressedColorIds, orientation).getDrawable();
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slAlphaRect(float normalAlpha, float pressAlpha, @ColorRes int colorId, float... radii) {
        Drawable normalDrawable = alphaRect(normalAlpha, colorId, radii).getDrawable();
        Drawable pressedDrawable = alphaRect(pressAlpha, colorId, radii).getDrawable();
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slAlphaCircle(float normalAlpha, float pressAlpha, @ColorRes int colorId) {
        Drawable normalDrawable = alphaCircle(normalAlpha, colorId).getDrawable();
        Drawable pressedDrawable = alphaCircle(pressAlpha, colorId).getDrawable();
        return selector(normalDrawable, pressedDrawable);
    }

    private static Drawable getDrawable(@DrawableRes int drawableId) {
        return ContextCompat.getDrawable(getContext(), drawableId);
    }

    private static int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }

    private static int getDp2Px(float dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private static boolean checkDrawableID(@DrawableRes int drawableId) {
        return drawableId != DRAWABLE_NONE;
    }

    private static boolean checkDrawable(Object obj) {
        return obj instanceof Drawable;
    }

    private static boolean checkColorId(@ColorRes int colorId) {
        return colorId != COLOR_NONE;
    }

    /**
     * This is the encapsulation of SelectDrawable based on StateListDrawable.
     */
    public static class SLWrapper {
        private StateListDrawable mDrawable;

        private SLWrapper() {
            mDrawable = new StateListDrawable();
        }

        public void setView(View view) {
            ViewCompat.setBackground(view, mDrawable);
            view.setClickable(true);
        }

        private SLWrapper selector(@DrawableRes int normalResId, @DrawableRes int pressedResId, @DrawableRes int checkedResId) {
            if (checkDrawableID(pressedResId)) {
                mDrawable.addState(new int[]{
                        android.R.attr.state_pressed
                }, XmlDrawableUtil.getDrawable(pressedResId));
            }
            if (checkDrawableID(checkedResId)) {
                mDrawable.addState(new int[]{
                        android.R.attr.state_checked
                }, XmlDrawableUtil.getDrawable(checkedResId));
            }
            if (checkDrawableID(normalResId)) {
                mDrawable.addState(new int[]{}, XmlDrawableUtil.getDrawable(normalResId));
            }
            return this;
        }

        /**
         * When setting StateListDrawable, the normal attribute must be placed last,
         * because it is filtered according to the order of addition.
         */
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

        public StateListDrawable getDrawable() {
            return mDrawable;
        }
    }

    /**
     * This is the encapsulation of ShapeDrawable based on GradientDrawable
     * which provides a way to change the shape.
     */
    public static class GDWrapper {
        private GradientDrawable mDrawable;

        private GDWrapper() {
            mDrawable = new GradientDrawable();
        }

        public void setView(View view) {
            ViewCompat.setBackground(view, mDrawable);
        }

        private GDWrapper color(@ColorRes int colorId) {
            if (checkColorId(colorId)) {
                mDrawable.setColor(getColor(colorId));
            }
            return this;
        }

        private GDWrapper circle() {
            mDrawable.setShape(GradientDrawable.OVAL);
            return this;
        }

        private GDWrapper rect(float radius) {
            mDrawable.setShape(GradientDrawable.RECTANGLE);
            mDrawable.setCornerRadius(getDp2Px(radius));
            return this;
        }

        private GDWrapper rect(float[] radii) {
            mDrawable.setShape(GradientDrawable.RECTANGLE);
            if (radii.length == 0) {
                return this;
            }
            if (radii.length == 1) {
                mDrawable.setCornerRadius(getDp2Px(radii[0]));
            } else if (radii.length == 4) {
                mDrawable.setCornerRadii(new float[]{
                        getDp2Px(radii[0]), getDp2Px(radii[0]), getDp2Px(radii[1]), getDp2Px(radii[1]),
                        getDp2Px(radii[2]), getDp2Px(radii[2]), getDp2Px(radii[3]), getDp2Px(radii[3])
                });
            } else if (radii.length == 8) {
                mDrawable.setCornerRadii(new float[]{
                        getDp2Px(radii[0]), getDp2Px(radii[1]), getDp2Px(radii[2]), getDp2Px(radii[3]),
                        getDp2Px(radii[4]), getDp2Px(radii[5]), getDp2Px(radii[6]), getDp2Px(radii[7])
                });
            } else {
                throw new RuntimeException("radii.length must be one of 1, 4 and 10");
            }
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

        private GDWrapper alpha(float alpha) {
            if (alpha >= 0 && alpha <= 1.0f) {
                mDrawable.setAlpha((int) (alpha * 0xff));
            }
            return this;
        }

        private GDWrapper stroke(@ColorRes int strokeColorId, float strokeWidth) {
            if (checkDrawableID(strokeColorId)) {
                mDrawable.setStroke(getDp2Px(strokeWidth), getColor(strokeColorId));
            }
            return this;
        }

        public GradientDrawable getDrawable() {
            return mDrawable;
        }
    }
}
