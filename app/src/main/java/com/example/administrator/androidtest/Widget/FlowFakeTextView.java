package com.example.administrator.androidtest.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.androidtest.R;

import java.util.ArrayList;
import java.util.List;

public class FlowFakeTextView extends View {
    private static final String TAG = "FlowFakeTextView";
    private int mTvLayoutId = View.NO_ID;
    private int mColor = Color.WHITE;
    // 分割的TextView
    private List<TextView> mSplitTvList = new ArrayList<>();
    // 保存分割点
    private List<Integer> mSplitPointList = new ArrayList<>();
    private TvInitCallback mTvInitCallback;

    public FlowFakeTextView(Context context) {
        this(context, null);
    }

    public FlowFakeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowFakeTextView);
        mTvLayoutId = typedArray.getResourceId(R.styleable.FlowFakeTextView_fstv_tv_layout_id, View.NO_ID);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }

    /**
     * 分割charSequence为TextView数组
     * startWidth: 首行宽度
     * contentWidth: 其他行宽度
     */
    private List<TextView> splitToTextView(CharSequence charSequence, int startWidth, int contentWidth) {
        mSplitPointList.clear();
        mSplitPointList.add(0);

        List<TextView> splitTvList = new ArrayList<>();
        final TextView startTv = createTv();
        int paddingHor = startTv.getPaddingLeft() + startTv.getPaddingRight();
        startWidth = startWidth - paddingHor;

        int startPos = 0;
        // width小于0，获取Layout crash
        if (startWidth > 0) {
            Layout startLayout = obtainLayout(startTv, charSequence, startWidth);
            startPos = startLayout.getLineEnd(0);
            // 首行处理
            while (startLayout.getLineWidth(0) >= startWidth && startPos > 0) {
                startPos--;
                startLayout = obtainLayout(startTv, charSequence.subSequence(0, startPos), startWidth);
            }
            if (startPos > 0) {
                startTv.setText(charSequence.subSequence(0, startPos));
                splitTvList.add(startTv);
                mSplitPointList.add(startPos);
            }
        }

        if (startPos < charSequence.length()) {
            CharSequence contentCs = charSequence.subSequence(startPos, charSequence.length());
            TextView contentTv = createTv();
            contentWidth = contentWidth - contentTv.getPaddingLeft() - contentTv.getPaddingRight();
            Layout contentLayout = obtainLayout(contentTv, contentCs, contentWidth);
            int lineCount = contentLayout.getLineCount();
            int lineStart = 0;
            int lineEnd = 0;
            for (int i = 0; i < lineCount; i++) {
                TextView tv = createTv();
                lineEnd = contentLayout.getLineEnd(i);
                tv.setText(contentCs.subSequence(lineStart, lineEnd));
                lineStart = lineEnd;
                splitTvList.add(tv);
                mSplitPointList.add(lineEnd + startPos);
            }
        }
        return splitTvList;
    }

    private Layout obtainLayout(TextView tv, CharSequence charSequence, int contentWidth) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder builder = StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), tv.getPaint(), contentWidth);
            builder.setAlignment(Layout.Alignment.ALIGN_NORMAL);
            builder.setIncludePad(tv.getIncludeFontPadding());
            builder.setLineSpacing(tv.getLineSpacingExtra(), tv.getLineSpacingMultiplier());
            return builder.build();
        } else {
            return new StaticLayout(charSequence, tv.getPaint(), contentWidth, Layout.Alignment.ALIGN_NORMAL,
                    tv.getLineSpacingMultiplier(), tv.getLineSpacingExtra(), tv.getIncludeFontPadding());
        }
    }

    public void setText(final CharSequence charSequence, final int totalWidth) {
        ViewGroup viewGroup = (ViewGroup) getParent();
        for (TextView textView : mSplitTvList) {
            viewGroup.removeView(textView);
        }
        int index = viewGroup.indexOfChild(FlowFakeTextView.this);
        int startWidth = totalWidth - getLeft() - viewGroup.getPaddingRight();
        int contentWidth = totalWidth - viewGroup.getPaddingLeft() - viewGroup.getPaddingRight();
        Log.d(TAG, "setText: startWidth = " + startWidth + ", contentWidth = " + contentWidth);
        // width小于0，获取Layout crash
        if (startWidth < 0 || contentWidth < 0) {
            return;
        }
        try {
            mSplitTvList = splitToTextView(charSequence, startWidth, contentWidth);
        } catch (Exception e) {
            // 兜底保护
            mSplitTvList.clear();
            TextView defaultTv = createTv();
            defaultTv.setText(charSequence);
            mSplitTvList.add(defaultTv);
        }
        for (TextView textView : mSplitTvList) {
            index ++;
            viewGroup.addView(textView, index);
        }
    }

    public void setText(final CharSequence charSequence, final List<Integer> splitList) {
        ViewGroup viewGroup = (ViewGroup) getParent();
        for (TextView textView : mSplitTvList) {
            viewGroup.removeView(textView);
        }
        int index = viewGroup.indexOfChild(FlowFakeTextView.this);
        List<Integer> newSplitList = new ArrayList<>(splitList);
        for (int i = 0; i < newSplitList.size() - 1; i++) {
            index ++;
            CharSequence sequence = charSequence.subSequence(splitList.get(i), splitList.get(i + 1));
            TextView textView = createTv();
            textView.setText(sequence);
            viewGroup.addView(textView, index);
            mSplitTvList.add(textView);
        }
    }

    private TextView createTv() {
        TextView tv = null;
        if (mTvLayoutId != View.NO_ID) {
            View view = LayoutInflater.from(getContext()).inflate(mTvLayoutId, null);
            if (view instanceof TextView) {
                tv = (TextView) view;
            }
        }
        if (tv == null && mTvInitCallback != null) {
            tv = mTvInitCallback.onGetInitTv();
        }
        if (tv == null) {
            tv = new TextView(getContext());
        }
        tv.setTextColor(mColor);
        return tv;
    }

    public void setTvInitCallback(TvInitCallback tvInitCallback) {
        mTvInitCallback = tvInitCallback;
    }

    public List<Integer> getSplitPointList() {
        return new ArrayList<>(mSplitPointList);
    }

    public void setTextColor(int color) {
        mColor = color;
        for (TextView textView : mSplitTvList) {
            textView.setTextColor(color);
        }
    }

    public interface TvInitCallback {
        TextView onGetInitTv();
    }
}
