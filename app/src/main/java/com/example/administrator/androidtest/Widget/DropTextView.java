package com.example.administrator.androidtest.Widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.PopupWindow;

import com.example.administrator.androidtest.Common.Util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class DropTextView extends android.support.v7.widget.AppCompatEditText implements TextWatcher {
    private static final int RESULT_TEXT_WINDOW_HEIGHT = 500;
    private List<String> mResultArray = new ArrayList<>();
    private ResultTextWindow mResultTextWindow;
    public DropTextView(Context context) {
        this(context, null);
    }

    public DropTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mResultTextWindow = new ResultTextWindow(getMeasuredWidth(), RESULT_TEXT_WINDOW_HEIGHT);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    class ResultTextWindow extends PopupWindow{
        public ResultTextWindow(int width, int height) {
            super(width, height);
        }
    }

    public void setResultArray(List<String> resultArray){
        if(resultArray != null) {
            mResultArray = resultArray;
        }
    }

    public interface IResultTextListener{
        void onResult(String text, int pos);
    }
}
