package com.example.administrator.androidtest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.example.administrator.androidtest.R;

public class TextCountView extends AppCompatTextView implements TextWatcher {
    private static final int LIMIT_COUNT = 100;
    private int mLimitCount;

    public TextCountView(Context context) {
        this(context, null);
    }

    public TextCountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextCountView);
        mLimitCount = array.getInteger(R.styleable.TextCountView_tcv_limit_count, LIMIT_COUNT);
        array.recycle();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int len = s.length();
        len = (len > mLimitCount ? mLimitCount : len);
        setCount(len);
    }

    private void setCount(int len){
        String str = len + " / " + mLimitCount;
        setText(str);
    }
}
