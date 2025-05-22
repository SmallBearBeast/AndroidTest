package com.example.administrator.androidtest.demo.ViewDemo.EditTextTest;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;

import com.bear.libcommon.manager.KeyBoardManager;
import com.bear.libcommon.util.ToastUtil;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.ViewDemo.BaseViewDemoComponent;

public class EditTextTestComponent extends BaseViewDemoComponent {

    private EditText noShowKeyboardEditText;

    public EditTextTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        getBinding().formatTextButton.setOnClickListener(this);
        getBinding().noShowKeyboardButton.setOnClickListener(this);
        noShowKeyboardEditText = getBinding().noShowKeyboardEditText;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.formatTextButton:
                testFormatText();
                break;
            case R.id.noShowKeyboardButton:
                // EditText get focus but not to show input keyboard
                testNoShowKeyboard();
                break;
            default:
                break;
        }
    }

    private void testFormatText() {
        TextView textTv = getBinding().formatTextEditText;
        textTv.setText(getContext().getString(R.string.format_string_text, "hugo.wu", 22));
    }

    private void testNoShowKeyboard() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            noShowKeyboardEditText.setShowSoftInputOnFocus(false);
            noShowKeyboardEditText.setCursorVisible(false);
            noShowKeyboardEditText.setOnClickListener(v -> {
                ToastUtil.showToast("Click No Show Input Keyboard EditText");
                KeyBoardManager.get().hideKeyBoard(getContext(), v);
            });
            noShowKeyboardEditText.setOnLongClickListener(null);
            noShowKeyboardEditText.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    KeyBoardManager.get().hideKeyBoard(getContext(), v);
                }
            });
            noShowKeyboardEditText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            noShowKeyboardEditText.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);
        }
    }
}
