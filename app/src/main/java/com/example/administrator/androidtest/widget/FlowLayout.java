package com.example.administrator.androidtest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.androidtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局
 */
public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";
    private final int mVerticalSpace;
    private final int mHorizontalSpace;
    private final List<Integer> mLineHeightList = new ArrayList<>();
    private OnFlowClickListener mFlowClickListener;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        mVerticalSpace = typedArray.getDimensionPixelOffset(R.styleable.FlowLayout_fl_vertical_space, 0);
        mHorizontalSpace = typedArray.getDimensionPixelOffset(R.styleable.FlowLayout_fl_horizontal_space, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mLineHeightList.clear();
        int childCount = getChildCount();
        // When width = wrap_content, the widthSize equal match_parent.
        int widthSize = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int useHeight = 0;
        int useWidth = 0;
        int lineMaxHeight = 0;
        int marginWidth = 0;
        int marginHeight = 0;
        boolean moreLine = false;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) {
                continue;
            }
            if (childView.getLayoutParams() instanceof MarginLayoutParams) {
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);
                MarginLayoutParams marginLp = (MarginLayoutParams) childView.getLayoutParams();
                marginHeight = marginLp.topMargin + marginLp.bottomMargin;
                marginWidth = marginLp.leftMargin + marginLp.rightMargin;
            } else {
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            }
            if (useWidth != 0) {
                useWidth = useWidth + mHorizontalSpace;
            }
            if (useWidth + childView.getMeasuredWidth() + marginWidth > widthSize) {
                moreLine = true;
                useWidth = 0;
                if (lineMaxHeight != 0) {
                    useHeight = useHeight + mVerticalSpace;
                }
                useHeight = useHeight + lineMaxHeight;
                mLineHeightList.add(lineMaxHeight);
                lineMaxHeight = 0;
            }
            useWidth = useWidth + childView.getMeasuredWidth() + marginWidth;
            lineMaxHeight = Math.max(lineMaxHeight, childView.getMeasuredHeight() + marginHeight);
        }
        useHeight = useHeight + lineMaxHeight;
        mLineHeightList.add(lineMaxHeight);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST && !moreLine) {
            measureWidth = useWidth + getPaddingLeft() + getPaddingRight();
        }
        setMeasuredDimension(measureWidth, MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY ?
                MeasureSpec.getSize(heightMeasureSpec) : useHeight + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int L = getPaddingLeft(), T = getPaddingTop();
        int lineIndex = 0;
        int lineMaxHeight = 0;
        int usefulWidth = getMeasuredWidth() - getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) {
                continue;
            }
            int leftMargin = 0;
            int rightMargin = 0;
            if (childView.getLayoutParams() instanceof MarginLayoutParams) {
                MarginLayoutParams marginLp = (MarginLayoutParams) childView.getLayoutParams();
                leftMargin = marginLp.leftMargin;
                rightMargin = marginLp.rightMargin;
            }
            if (L != getPaddingLeft()) {
                L = L + mHorizontalSpace;
            }
            if (L + childView.getMeasuredWidth() + leftMargin + rightMargin > usefulWidth) {
                if (lineMaxHeight != 0) {
                    T = T + mVerticalSpace;
                }
                T = T + lineMaxHeight;
                L = getPaddingLeft();
                lineIndex++;
            }
            lineMaxHeight = lineIndex < mLineHeightList.size() ? mLineHeightList.get(lineIndex) : lineMaxHeight;
            l = L + leftMargin;
            t = T + (lineMaxHeight - childView.getMeasuredHeight()) / 2;
            r = l + childView.getMeasuredWidth();
            b = t + childView.getMeasuredHeight();
            childView.layout(l, t, r, b);
            L = L + childView.getMeasuredWidth() + leftMargin + rightMargin;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(super.generateDefaultLayoutParams());
    }

    public interface OnFlowClickListener {
        void onClick(View view);
    }

    public void setFlowClickListener(final OnFlowClickListener flowClickListener) {
        mFlowClickListener = flowClickListener;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (flowClickListener != null) {
                childView.setOnClickListener(flowClickListener::onClick);
            } else {
                childView.setOnClickListener(null);
            }

        }
    }

    @Override
    public void onViewAdded(View child) {
        if (mFlowClickListener != null) {
            child.setOnClickListener(v -> mFlowClickListener.onClick(v));
        }
    }

    @Override
    public void onViewRemoved(View child) {
        child.setOnClickListener(null);
    }
}
