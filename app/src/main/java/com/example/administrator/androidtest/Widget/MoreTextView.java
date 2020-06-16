package com.example.administrator.androidtest.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.administrator.androidtest.R;

import java.lang.reflect.Field;

public class MoreTextView extends AppCompatTextView {
    private static final String TAG = MoreTextView.class.getSimpleName();
    private static final String ELLIPSIS_STRING = new String(new char[]{'\u2026'});
    private static final String DEFAULT_OPEN_SUFFIX = " 展开";
    private static final String DEFAULT_CLOSE_SUFFIX = " 收起";
    private boolean mExpandable;
    private boolean mCloseInNewLine;
    private boolean mHasAnimation = false;
    private boolean mIsAnimating = false;
    private boolean mIsClosed = false;

    private int mMaxLines;
    private int mViewWidth = 0;
    private int mOpenHeight;
    private int mCLoseHeight;
    private int mOpenSuffixColor;
    private int mCloseSuffixColor;

    private String mOpenSuffixStr = DEFAULT_OPEN_SUFFIX;
    private String mCloseSuffixStr = DEFAULT_CLOSE_SUFFIX;
    private SpannableStringBuilder mOpenSpanBuilder;
    private SpannableStringBuilder mCloseSpanBuilder;
    private SpannableString mOpenSuffixSpan;
    private SpannableString mCloseSuffixSpan;

    private Animation mOpenAnim;
    private Animation mCloseAnim;
    private OpenAndCloseCallback mOpenCloseCallback;

    public MoreTextView(Context context) {
        this(context, null);
    }

    public MoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeArray(context, attrs);
    }

    private void initTypeArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MoreTextView);
        mCloseInNewLine = typedArray.getBoolean(R.styleable.MoreTextView_mtv_close_in_newLine, false);
        mHasAnimation = typedArray.getBoolean(R.styleable.MoreTextView_mtv_has_animation, false);
        mOpenSuffixColor = typedArray.getColor(R.styleable.MoreTextView_mtv_open_suffix_color, Color.parseColor("#F23030"));
        mCloseSuffixColor = typedArray.getColor(R.styleable.MoreTextView_mtv_close_suffix_color, Color.parseColor("#F23030"));
        mOpenSuffixStr = typedArray.getString(R.styleable.MoreTextView_mtv_open_suffix_str);
        if (mOpenSuffixStr == null) {
            mOpenSuffixStr = DEFAULT_OPEN_SUFFIX;
        }
        mCloseSuffixStr = typedArray.getString(R.styleable.MoreTextView_mtv_close_suffix_str);
        if (mCloseSuffixStr == null) {
            mCloseSuffixStr = DEFAULT_CLOSE_SUFFIX;
        }
        typedArray.recycle();
        mMaxLines = getMaxLines();
        setMovementMethod(OverLinkMovementMethod.getInstance());
        setIncludeFontPadding(false);
        updateOpenSuffixSpan();
        updateCloseSuffixSpan();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != 0 && mViewWidth != w) {
            mViewWidth = w;
            setOriginalText(getText());
        }
    }

    public void setOriginalText(final CharSequence text) {
        setMaxLines(mMaxLines);
        if (mViewWidth != 0) {
            new SetTextTask(text).run();
        } else {
            post(new SetTextTask(text));
        }
    }

    private void setUpOpenSpanBuilder(CharSequence text) {
        mOpenSpanBuilder = new SpannableStringBuilder(text);
        // 拼接展开内容
        if (mCloseInNewLine) {
            mOpenSpanBuilder.append("\n");
        }
        if (mCloseSuffixSpan != null) {
            mOpenSpanBuilder.append(mCloseSuffixSpan);
        }
    }

    private void setUpCloseSpanBuilder(CharSequence text) {
        mCloseSpanBuilder = new SpannableStringBuilder(text);
        Layout tempLayout = obtainStaticLayout(mCloseSpanBuilder);
        int maxLines = getMaxLines();
        if (maxLines != -1) {
            mExpandable = tempLayout.getLineCount() > maxLines;
            if (mExpandable) {
                // 计算原文截取位置
                int endPos = tempLayout.getLineEnd(maxLines - 1);
                mCloseSpanBuilder = new SpannableStringBuilder(text.subSequence(0, endPos));
                int cutLength = mCloseSpanBuilder.length();
                mCloseSpanBuilder.append(ELLIPSIS_STRING);
                if (mOpenSuffixSpan != null) {
                    mCloseSpanBuilder.append(mOpenSuffixSpan);
                }
                // 循环判断，收起内容添加展开后缀后的内容
                tempLayout = obtainStaticLayout(mCloseSpanBuilder);
                while (tempLayout.getLineCount() > maxLines) {
                    cutLength--;
                    if (cutLength == -1) {
                        break;
                    }
                    mCloseSpanBuilder = new SpannableStringBuilder(text.subSequence(0, cutLength)).append(ELLIPSIS_STRING);
                    if (mOpenSuffixSpan != null) {
                        mCloseSpanBuilder.append(mOpenSuffixSpan);
                    }
                    tempLayout = obtainStaticLayout(mCloseSpanBuilder);
                }
                //计算收起的文本高度
                mCLoseHeight = tempLayout.getHeight() + getPaddingTop() + getPaddingBottom();
            }
        }
    }

    private void switchOpenClose() {
        if (mExpandable) {
            mIsClosed = !mIsClosed;
            if (mIsClosed) {
                close();
            } else {
                open();
            }
        }
    }

    /**
     * 设置是否有动画
     */
    public void setHasAnimation(boolean hasAnimation) {
        mHasAnimation = hasAnimation;
    }

    /**
     * 展开
     */
    private void open() {
        if (mHasAnimation) {
            Layout layout = obtainStaticLayout(mOpenSpanBuilder);
            mOpenHeight = layout.getHeight() + getPaddingTop() + getPaddingBottom();
            executeOpenAnim();
        } else {
            MoreTextView.super.setMaxLines(Integer.MAX_VALUE);
            setText(mOpenSpanBuilder);
            if (mOpenCloseCallback != null) {
                mOpenCloseCallback.onOpen();
            }
        }
    }

    /**
     * 收起
     */
    private void close() {
        setMaxLines(mMaxLines);
        if (mHasAnimation) {
            executeCloseAnim();
        } else {
            MoreTextView.super.setMaxLines(getMaxLines());
            setText(mCloseSpanBuilder);
            if (mOpenCloseCallback != null) {
                mOpenCloseCallback.onClose();
            }
        }
    }

    /**
     * 执行展开动画
     */
    private void executeOpenAnim() {
        if (mOpenAnim == null) {
            mOpenAnim = new ExpandCollapseAnimation(this, mCLoseHeight, mOpenHeight);
            mOpenAnim.setFillAfter(true);
            mOpenAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    MoreTextView.super.setMaxLines(Integer.MAX_VALUE);
                    setText(mOpenSpanBuilder);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //  动画结束后textview设置展开的状态
                    getLayoutParams().height = mOpenHeight;
                    requestLayout();
                    mIsAnimating = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        if (mIsAnimating) {
            return;
        }
        mIsAnimating = true;
        clearAnimation();
        startAnimation(mOpenAnim);
    }

    /**
     * 执行收起动画
     */
    private void executeCloseAnim() {
        if (mCloseAnim == null) {
            mCloseAnim = new ExpandCollapseAnimation(this, mOpenHeight, mCLoseHeight);
            mCloseAnim.setFillAfter(true);
            mCloseAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mIsAnimating = false;
                    MoreTextView.super.setMaxLines(getMaxLines());
                    setText(mCloseSpanBuilder);
                    getLayoutParams().height = mCLoseHeight;
                    requestLayout();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        if (mIsAnimating) {
            return;
        }
        mIsAnimating = true;
        clearAnimation();
        startAnimation(mCloseAnim);
    }

    private Layout obtainStaticLayout(SpannableStringBuilder spannable) {
        int contentWidth = mViewWidth - getPaddingLeft() - getPaddingRight();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder builder = StaticLayout.Builder.obtain(spannable, 0, spannable.length(), getPaint(), contentWidth);
            builder.setAlignment(Layout.Alignment.ALIGN_NORMAL);
            builder.setIncludePad(getIncludeFontPadding());
            builder.setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier());
            return builder.build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return new StaticLayout(spannable, getPaint(), contentWidth, Layout.Alignment.ALIGN_NORMAL,
                    getLineSpacingMultiplier(), getLineSpacingExtra(), getIncludeFontPadding());
        } else {
            return new StaticLayout(spannable, getPaint(), contentWidth, Layout.Alignment.ALIGN_NORMAL,
                    getFloatField("mSpacingMult", 1f), getFloatField("mSpacingAdd", 0f), getIncludeFontPadding());
        }
    }

    private float getFloatField(String fieldName, float defaultValue) {
        float value = defaultValue;
        if (TextUtils.isEmpty(fieldName)) {
            return value;
        }
        try {
            // 获取该类的所有属性值域
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (TextUtils.equals(fieldName, field.getName())) {
                    value = field.getFloat(this);
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 更新展开后缀Spannable
     */
    private void updateOpenSuffixSpan() {
        if (TextUtils.isEmpty(mOpenSuffixStr)) {
            mOpenSuffixSpan = null;
            return;
        }
        mOpenSuffixSpan = new SpannableString(mOpenSuffixStr);
        mOpenSuffixSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, mOpenSuffixStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mOpenSuffixSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                switchOpenClose();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(mOpenSuffixColor);
                ds.setUnderlineText(false);
            }
        }, 0, mOpenSuffixStr.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    }


    /**
     * 更新收起后缀Spannable
     */
    private void updateCloseSuffixSpan() {
        if (TextUtils.isEmpty(mCloseSuffixStr)) {
            mCloseSuffixSpan = null;
            return;
        }
        mCloseSuffixSpan = new SpannableString(mCloseSuffixStr);
        mCloseSuffixSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, mCloseSuffixStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (mCloseInNewLine) {
            AlignmentSpan alignmentSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE);
            mCloseSuffixSpan.setSpan(alignmentSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mCloseSuffixSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                switchOpenClose();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(mCloseSuffixColor);
                ds.setUnderlineText(false);
            }
        }, 1, mCloseSuffixStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    private class SetTextTask implements Runnable {
        private CharSequence mText;

        private SetTextTask(CharSequence text) {
            mText = text;
        }

        @Override
        public void run() {
            if (getMaxLines() == Integer.MAX_VALUE) {
                setText(mText);
                return;
            }
            Layout tempLayout = obtainStaticLayout(new SpannableStringBuilder(mText));
            if (tempLayout.getLineCount() <= getMaxLines()) {
                setText(mText);
                return;
            }
            mExpandable = false;
            setUpOpenSpanBuilder(mText);
            setUpCloseSpanBuilder(mText);
            mIsClosed = mExpandable;
            setText(mExpandable ? mCloseSpanBuilder : mOpenSpanBuilder);
        }
    }

    private static class ExpandCollapseAnimation extends Animation {
        private final View mTargetView;//动画执行view
        private final int mStartHeight;//动画执行的开始高度
        private final int mEndHeight;//动画结束后的高度

        ExpandCollapseAnimation(View target, int startHeight, int endHeight) {
            mTargetView = target;
            mStartHeight = startHeight;
            mEndHeight = endHeight;
            setDuration(400);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            mTargetView.setScrollY(0);
            //计算出每次应该显示的高度,改变执行view的高度，实现动画
            mTargetView.getLayoutParams().height = (int) ((mEndHeight - mStartHeight) * interpolatedTime + mStartHeight);
            mTargetView.requestLayout();
        }
    }

    /**
     * 设置展开后缀text
     */
    public void setOpenSuffix(String openSuffix) {
        mOpenSuffixStr = openSuffix;
        updateOpenSuffixSpan();
    }

    /**
     * 设置展开后缀文本颜色
     */
    public void setOpenSuffixColor(@ColorInt int openSuffixColor) {
        mOpenSuffixColor = openSuffixColor;
        updateOpenSuffixSpan();
    }

    /**
     * 设置收起后缀text
     */
    public void setCloseSuffix(String closeSuffix) {
        mCloseSuffixStr = closeSuffix;
        updateCloseSuffixSpan();
    }

    /**
     * 设置收起后缀文本颜色
     */
    public void setCloseSuffixColor(@ColorInt int closeSuffixColor) {
        mCloseSuffixColor = closeSuffixColor;
        updateCloseSuffixSpan();
    }

    /**
     * 收起后缀是否另起一行
     */
    public void setCloseInNewLine(boolean closeInNewLine) {
        mCloseInNewLine = closeInNewLine;
        updateCloseSuffixSpan();
    }

    public void setOpenAndCloseCallback(OpenAndCloseCallback callback) {
        mOpenCloseCallback = callback;
    }

    public interface OpenAndCloseCallback {
        void onOpen();

        void onClose();
    }
}
