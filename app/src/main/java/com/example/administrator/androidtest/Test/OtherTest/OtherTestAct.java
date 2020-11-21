package com.example.administrator.androidtest.Test.OtherTest;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bear.libcomponent.ComponentAct;
import com.bear.libkv.AppVal.SpVal;
import com.bear.libkv.MmpVal.MmkvVal;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.FullTextView.FullTextView;
import com.example.administrator.androidtest.Widget.FullTextView.TextOpt;
import com.example.administrator.androidtest.Widget.LikeView.LikeView;
import com.example.libbase.Util.KeyBoardUtil;
import com.example.libbase.Util.ThreadUtil;
import com.example.libbase.Util.ToastUtil;

import java.util.Random;

public class OtherTestAct extends ComponentAct {
    private FullTextView ftvFullText;
    private EditText editText;
    private LikeView likeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editText = findViewById(R.id.et_no_show_input_keyboard);
        ftvFullText = findViewById(R.id.ftv_full_text);
        likeView = findViewById(R.id.lv_like_heart);
        showSpVal();
    }

    @Override
    protected int layoutId() {
        return R.layout.act_other_test;
    }

    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.bt_format_text_click:
                TextView textTv = findViewById(R.id.tv_format_text);
                textTv.setText(getString(R.string.format_string_text, "hugo.wu", 22));
                break;

            case R.id.bt_no_show_input_keyboard_mask_click:
                // EditText get focus but not to show input keyboard
                setupNoShowInput();
                break;

            case R.id.bt_full_text_click:
                TextOpt bgOpt = TextOpt.bgOpt(0, 5, Color.RED);
                TextOpt fgOpt = TextOpt.fgOpt(5, ftvFullText.length(), Color.BLUE);
                ftvFullText.bg(bgOpt).fg(fgOpt).done();
                break;

            case R.id.lv_like_heart:
                if (likeView.isLike()) {
                    likeView.like();
                } else {
                    likeView.unLike();
                }
                break;

            case R.id.bt_back_start_delay_service_click:
                ThreadUtil.postOnMain(new Runnable() {
                    @Override
                    public void run() {
                        BackgroundService.start(OtherTestAct.this, BackgroundService.START);
                    }
                }, 5 * 1000);
                break;

            case R.id.bt_back_stop_delay_service_click:
                ThreadUtil.postOnMain(new Runnable() {
                    @Override
                    public void run() {
//                        BackgroundService.stop(OtherTestAct.this, 1);
                        BackgroundService.start(OtherTestAct.this, BackgroundService.STOP);
//                        stopService(new Intent(OtherTestAct.this, BackgroundService.class));

                    }
                }, 100 * 1000);
                break;

            case R.id.bt_sp_val_set_click:
                setSpVal();
                break;

            case R.id.bt_sp_val_get_click:
                showSpVal();
                break;

            case R.id.bt_move_to_mmkv_click:
                moveToMmkv();
                break;

            case R.id.bt_move_to_mmkv_show_click:
                showMmkv();
                break;
        }
    }

    private void setupNoShowInput() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(false);
            editText.setCursorVisible(false);
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast("Click No Show Input Keyboard EditText");
                    KeyBoardUtil.hideSoftInput(OtherTestAct.this, v);
                }
            });
            editText.setOnLongClickListener(null);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        KeyBoardUtil.hideSoftInput(OtherTestAct.this, v);
                    }
                }
            });
            editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
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
            editText.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);
        }
    }

    private void setSpVal() {
        Random random = new Random();
        int randomInt = random.nextInt(100);
        boolean randomBool = random.nextBoolean();
        randomInt = randomBool ? randomInt : - randomInt;
        SpValHelper.testBoolSp.set(randomBool);
        SpValHelper.testIntSp.set(randomInt);
        SpValHelper.testFloatSp.set(randomInt);
        SpValHelper.testStringSp.set(randomInt + "" + randomBool);
    }

    private void showSpVal() {
        TextView tvSpValTip = findViewById(R.id.tv_sp_val_tip);
        StringBuilder builder = new StringBuilder();
        builder.append("testBoolSp = ").append(SpValHelper.testBoolSp.get()).append("\n");
        builder.append("testIntSp = ").append(SpValHelper.testIntSp.get()).append("\n");
        builder.append("testFloatSp = ").append(SpValHelper.testFloatSp.get()).append("\n");
        builder.append("testStringSp = ").append(SpValHelper.testStringSp.get());
        tvSpValTip.setText(builder.toString());
    }

    private void moveToMmkv() {
        MmkvVal.importFromSharedPreferences(this, MmkvValHelper.MMKV_GLOBAL_CONFIG, SpValHelper.SP_GLOBAL_CONFIG);
        MmkvVal.importFromSharedPreferences(this, MmkvVal.DEFAULT_MMPVAL_ID, SpVal.DEFAULT_APPVAL_NAME);
    }

    private void showMmkv() {
        TextView tvMmkvValTip = findViewById(R.id.tv_move_to_mmkv_tip);
        StringBuilder builder = new StringBuilder();
        builder.append("testBoolSp = ").append(MmkvValHelper.testBoolSp.get()).append("\n");
        builder.append("testIntSp = ").append(MmkvValHelper.testIntSp.get()).append("\n");
        builder.append("testFloatSp = ").append(MmkvValHelper.testFloatSp.get()).append("\n");
        builder.append("testStringSp = ").append(MmkvValHelper.testStringSp.get());
        tvMmkvValTip.setText(builder.toString());
    }

}
