package com.example.administrator.androidtest.Widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class InputView extends FrameLayout implements View.OnClickListener {
    private String inputText = "";
    private TextView titleTv;
    private TextView hintTv;
    private EditText inputEt;
    private ImageView deleteIv;
    private ImageView noticeIv;
    private ViewGroup inputVg;
    private EditListener editListener;
    private NoticeListener noticeListener;

    public InputView(@NonNull Context context) {
        this(context, null);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        LayoutInflater.from(context).inflate(R.layout.view_input, this);
        initView();
        initTypeArray(context, attrs);
//        SoftKeyBoardTool.observeKeyBoard((Activity) context, (showKeyBoard, bottomOffset) -> {
//            if (!showKeyBoard) {
//                requestFocus(false);
//            }
//        });
    }

    private void initTypeArray(Context context, AttributeSet attrs) {
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputView);
//        String hint = typedArray.getString(R.styleable.InputView_iv_hint);
//        hintTv.setText(hint);
//        String title = typedArray.getString(R.styleable.InputView_iv_title);
//        titleTv.setText(title);
//        boolean isShowNotice = typedArray.getBoolean(R.styleable.InputView_iv_show_notice, false);
//        noticeIv.setVisibility(isShowNotice ? VISIBLE : GONE);
//        typedArray.recycle();
    }

    private void initView() {
//        titleTv = findViewById(R.id.tv_title);
//        inputVg = findViewById(R.id.cl_input_content);
//        hintTv = findViewById(R.id.tv_hint);
//        hintTv.setOnClickListener(this);
//        deleteIv = findViewById(R.id.iv_delete);
//        deleteIv.setOnClickListener(this);
//        noticeIv = findViewById(R.id.iv_notice);
//        noticeIv.setOnClickListener(this);
//        inputEt = findViewById(R.id.et_input);
//        inputEt.setOnClickListener(this);
//        inputEt.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                requestFocus(true);
//                if (!TextUtils.isEmpty(inputEt.getText())) {
//                    deleteIv.setVisibility(VISIBLE);
//                }
//            } else {
//                requestFocus(false);
//                deleteIv.setVisibility(INVISIBLE);
//                if (TextUtils.isEmpty(inputText)) {
//                    inputVg.setVisibility(INVISIBLE);
//                    hintTv.setVisibility(VISIBLE);
//                    hintTv.setOnClickListener(InputView.this);
//                }
//            }
//        });
        inputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkTextChanged(String.valueOf(s));
            }
        });
    }

    private void checkTextChanged(String text) {
        inputText = text;
        if (TextUtils.isEmpty(text)) {
            requestFocus(false);
            inputVg.setVisibility(INVISIBLE);
            deleteIv.setVisibility(INVISIBLE);
            hintTv.setVisibility(VISIBLE);
            hintTv.setOnClickListener(this);
        } else {
            deleteIv.setVisibility(VISIBLE);
        }
        if (editListener != null) {
            editListener.onEdit(this, text);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.et_input:
//            case R.id.tv_hint:
//                inputVg.setVisibility(VISIBLE);
//                hintTv.setVisibility(INVISIBLE);
//                hintTv.setOnClickListener(null);
//                requestFocus(true);
//                break;
//
//            case R.id.iv_delete:
//                inputEt.setText("");
//                break;
//
//            case R.id.iv_notice:
//                if (noticeListener != null) {
//                    noticeListener.onNotice();
//                }
//                break;
        }
    }

    public String getInputText() {
        return inputText;
    }

    public EditText getInputEt() {
        return inputEt;
    }

    private void showSoftInput(View view) {
        try {
            Context context = view.getContext();
            final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null) {
                view.requestFocus();
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        } catch (Exception e) {

        }
    }

    private void hideSoftInput(View view) {
        try {
            Context context = view.getContext();
            final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null) {
                view.clearFocus();
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {

        }
    }

    private void requestFocus(boolean focus) {
        if (inputEt.hasFocus() == focus) {
            return;
        }
        inputEt.setFocusable(focus);
        inputEt.setFocusableInTouchMode(focus);
        if (focus) {
            inputEt.requestFocus();
            showSoftInput(inputEt);
        } else {
            inputEt.clearFocus();
            hideSoftInput(inputEt);
        }
    }

    public void setEditListener(EditListener listener) {
        editListener = listener;
    }

    public void setNoticeListener(NoticeListener listener) {
        noticeListener = listener;
    }

    public interface EditListener {
        void onEdit(InputView inputView, String text);
    }

    public interface NoticeListener {
        void onNotice();
    }
}
