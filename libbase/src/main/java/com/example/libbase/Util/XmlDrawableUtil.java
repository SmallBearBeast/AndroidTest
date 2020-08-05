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

    public static GDWrapper rect(boolean isUseColorRes, int color, float... radii) {
        return new GDWrapper().color(isUseColorRes, color).rect(radii);
    }

    public static GDWrapper circle(boolean isUseColorRes, int color) {
        return new GDWrapper().color(isUseColorRes, color).circle();
    }

    public static GDWrapper strokeRect(boolean isUseColorRes, int color, int strokeColorId, float strokeWidth, float... radii) {
        return new GDWrapper().color(isUseColorRes, color).rect(radii).stroke(strokeColorId, strokeWidth);
    }

    public static GDWrapper strokeCircle(boolean isUseColorRes, int color, int strokeColorId, float strokeWidth) {
        return new GDWrapper().color(isUseColorRes, color).circle().stroke(strokeColorId, strokeWidth);
    }

    public static GDWrapper gradientRect(boolean isUseColorRes, int[] colors, GradientDrawable.Orientation orientation, float... radii) {
        return new GDWrapper().gradient(isUseColorRes, colors, orientation).rect(radii);
    }

    public static GDWrapper gradientCircle(boolean isUseColorRes, int[] colors, GradientDrawable.Orientation orientation) {
        return new GDWrapper().gradient(isUseColorRes, colors, orientation).circle();
    }

    public static GDWrapper alphaRect(boolean isUseColorRes, float alpha, int color, float... radii) {
        return new GDWrapper().color(isUseColorRes, color).alpha(alpha).rect(radii);
    }

    public static GDWrapper alphaCircle(boolean isUseColorRes, float alpha, int color) {
        return new GDWrapper().color(isUseColorRes, color).alpha(alpha).circle();
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

    public static SLWrapper slRect(boolean isUseColorRes, int normalColor, int pressedColor, float... radii) {
        Drawable normalDrawable = rect(isUseColorRes, normalColor, radii).getDrawable();
        Drawable pressedDrawable = rect(isUseColorRes, pressedColor, radii).getDrawable();
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slRect(boolean isUseColorRes, int normalColor, int pressedColor, int checkedColor, float... radii) {
        Drawable normalDrawable = rect(isUseColorRes, normalColor, radii).getDrawable();
        Drawable pressedDrawable = rect(isUseColorRes, pressedColor, radii).getDrawable();
        Drawable checkedDrawable = rect(isUseColorRes, checkedColor, radii).getDrawable();
        return selector(normalDrawable, pressedDrawable, checkedDrawable);
    }

    public static SLWrapper slCircle(boolean isUseColorRes, int normalColor, int pressedColor) {
        Drawable normalDrawable = circle(isUseColorRes, normalColor).getDrawable();
        Drawable pressedDrawable = circle(isUseColorRes, pressedColor).getDrawable();
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slCircle(boolean isUseColorRes, int normalColor, int pressedColor, int checkedColor) {
        Drawable normalDrawable = circle(isUseColorRes, normalColor).getDrawable();
        Drawable pressedDrawable = circle(isUseColorRes, pressedColor).getDrawable();
        Drawable checkedDrawable = circle(isUseColorRes, checkedColor).getDrawable();
        return selector(normalDrawable, pressedDrawable, checkedDrawable);
    }

    public static SLWrapper slGradientRect(boolean isUseColorRes, int[] normalColors, int[] pressedColors, GradientDrawable.Orientation orientation, float... radii) {
        Drawable normalDrawable = gradientRect(isUseColorRes, normalColors, orientation, radii).getDrawable();
        Drawable pressedDrawable = gradientRect(isUseColorRes, pressedColors, orientation, radii).getDrawable();
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slGradientCircle(boolean isUseColorRes, int[] normalColors, int[] pressedColors, GradientDrawable.Orientation orientation) {
        Drawable normalDrawable = gradientCircle(isUseColorRes, normalColors, orientation).getDrawable();
        Drawable pressedDrawable = gradientCircle(isUseColorRes, pressedColors, orientation).getDrawable();
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slAlphaRect(boolean isUseColorRes, float normalAlpha, float pressAlpha, int color, float... radii) {
        Drawable normalDrawable = alphaRect(isUseColorRes, normalAlpha, color, radii).getDrawable();
        Drawable pressedDrawable = alphaRect(isUseColorRes, pressAlpha, color, radii).getDrawable();
        return selector(normalDrawable, pressedDrawable);
    }

    public static SLWrapper slAlphaCircle(boolean isUseColorRes, float normalAlpha, float pressAlpha, int color) {
        Drawable normalDrawable = alphaCircle(isUseColorRes, normalAlpha, color).getDrawable();
        Drawable pressedDrawable = alphaCircle(isUseColorRes, pressAlpha, color).getDrawable();
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

        private GDWrapper color(boolean isUseColorRes, int color) {
            if (isUseColorRes) {
                if (checkColorId(color)) {
                    mDrawable.setColor(getColor(color));
                }
            } else {
                mDrawable.setColor(color);
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
                throw new RuntimeException("radii.length must be one of 1, 4 and 8");
            }
            return this;
        }

        private GDWrapper gradient(boolean isUseColorRes, int[] colors, GradientDrawable.Orientation orientation) {
            if (colors.length > 1) {
                int[] tempColors;
                if (isUseColorRes) {
                    tempColors = new int[colors.length];
                    for (int i = 0; i < tempColors.length; i++) {
                        if (checkColorId(tempColors[i])) {
                            tempColors[i] = getColor(tempColors[i]);
                        }
                    }
                } else {
                    tempColors = colors;
                }
                mDrawable.setColors(tempColors);
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
