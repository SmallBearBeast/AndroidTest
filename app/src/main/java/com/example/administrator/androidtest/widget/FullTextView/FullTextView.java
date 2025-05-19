package com.example.administrator.androidtest.widget.FullTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;


import com.example.administrator.androidtest.R;
import com.bear.libcommon.wrapper.TextWatcherWrapper;

public class FullTextView extends AppCompatTextView {
    private SpannableString mSpannableStr;

    public FullTextView(Context context) {
        this(context, null);
    }

    public FullTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSpannableStr = new SpannableString(getText());
        addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                mSpannableStr = new SpannableString(s);
            }
        });

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FullTextView);
        TextOpt opt = new TextOpt();
        opt.mBgColor = array.getColor(R.styleable.FullTextView_ftv_bg_color, Color.TRANSPARENT);
        opt.mFgColor = array.getColor(R.styleable.FullTextView_ftv_fg_color, getCurrentTextColor());
        opt.mStart = array.getInteger(R.styleable.FullTextView_ftv_start, 0);
        opt.mEnd = array.getInteger(R.styleable.FullTextView_ftv_end, getText().length());
        opt.mStyle = array.getInteger(R.styleable.FullTextView_ftv_style, Typeface.NORMAL);
        opt.mSize = (int) array.getDimension(R.styleable.FullTextView_ftv_size, getTextSize());
        array.recycle();
        bg(opt).fg(opt).style(opt).size(opt).done();
    }


    public FullTextView bg(TextOpt opt) {
        mSpannableStr.setSpan(new BackgroundColorSpan(opt.mBgColor), opt.mStart, opt.mEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return this;
    }

    public FullTextView click(final TextOpt opt) {
        mSpannableStr.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (opt.mRun != null) {
                    opt.mRun.run();
                }
            }
        }, opt.mStart, opt.mEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        setMovementMethod(LinkMovementMethod.get());
        return this;
    }

    public FullTextView fg(TextOpt opt) {
        mSpannableStr.setSpan(new ForegroundColorSpan(opt.mFgColor), opt.mStart, opt.mEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return this;
    }

    public FullTextView deleteLine(TextOpt opt) {
        mSpannableStr.setSpan(new StrikethroughSpan(), opt.mStart, opt.mEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return this;
    }

    public FullTextView underLine(TextOpt opt) {
        mSpannableStr.setSpan(new UnderlineSpan(), opt.mStart, opt.mEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return this;
    }

    public FullTextView style(TextOpt opt) {
        mSpannableStr.setSpan(new StyleSpan(opt.mStyle), opt.mStart, opt.mEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return this;
    }

    public FullTextView url(TextOpt opt) {
        mSpannableStr.setSpan(new URLSpan(opt.mUrl), opt.mStart, opt.mEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return this;
    }

    public FullTextView size(TextOpt opt) {
        mSpannableStr.setSpan(new AbsoluteSizeSpan(opt.mSize), opt.mStart, opt.mEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return this;
    }

    public void done() {
        setText(mSpannableStr);
    }
}
