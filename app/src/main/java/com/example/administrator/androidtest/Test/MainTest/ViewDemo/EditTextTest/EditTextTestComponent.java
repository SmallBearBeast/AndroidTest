package com.example.administrator.androidtest.Test.MainTest.ViewDemo.EditTextTest;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;
import com.example.libbase.Manager.KeyBoardManager;
import com.example.libbase.Util.ToastUtil;

public class EditTextTestComponent extends TestComponent {

    private EditText noShowKeyboardEditText;

    @Override
    protected void onCreate() {
        noShowKeyboardEditText = findViewById(R.id.noShowKeyboardEditText);
        setOnClickListener(this, R.id.formatTextButton, R.id.noShowKeyboardButton);
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
        TextView textTv = findViewById(R.id.formatTextEditText);
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
