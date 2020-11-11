package com.example.administrator.androidtest.Test.FragTest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.ToastUtil;

public class FragTestAct extends ComponentAct implements TestFrag.FragmentInteractionListener {
    private TestFrag testFrag_1 = TestFrag.get("testFrag_1");
    private TestFrag testFrag_2 = TestFrag.get("testFrag_2");
    private TestFrag testFrag_3 = TestFrag.get("testFrag_3");
    private TestFrag testFrag_4 = TestFrag.get("testFrag_4");
    private TestFrag testFrag_5 = TestFrag.get("testFrag_5");

    private FragmentManager fragmentManager;

    @Override
    protected int layoutId() {
        return R.layout.act_frag_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fl_frag_container, testFrag_1).commit();
        //        // commit不会立马触发Fragment生命周期
        //        getSupportFragmentManager().beginTransaction().add(testFrag, "Hello").commit();
        //        // executePendingTransactions可以立即触发Fragment生命周期
        //        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onContentChanged() {
        Log.d(TAG, "onContentChanged: ");
        super.onContentChanged();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        Log.d(TAG, "onAttachFragment: ");
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d(TAG, "onWindowFocusChanged: hasFocus = " + hasFocus);
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow: ");
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow: ");
        super.onDetachedFromWindow();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        // FragmentActivity的onStart方法有execPendingActions，因此正常Activity走到onStart，Fragment才开始走生命周期。
        super.onStart();
    }

    // 每次操作时候需要beginTransaction(）,不能复用同一个FragmentTransaction，否则会抛出commit already called异常。
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                // 重复add会抛出Fragment already added异常
                fragmentManager.beginTransaction().add(R.id.fl_frag_container, testFrag_2).commit();
                break;

            case R.id.bt_2:
                fragmentManager.beginTransaction().remove(testFrag_2).commit();
                break;

            case R.id.bt_3:
                fragmentManager.beginTransaction().show(testFrag_2).commit();
                break;

            case R.id.bt_4:
                fragmentManager.beginTransaction().hide(testFrag_2).commit();
                break;

            case R.id.bt_5:
                fragmentManager.beginTransaction().attach(testFrag_2).commit();
                break;

            case R.id.bt_6:
                fragmentManager.beginTransaction().detach(testFrag_2).commit();
                break;

            case R.id.bt_7:
                fragmentManager.beginTransaction().replace(R.id.fl_frag_container, testFrag_3).commit();
                break;

            case R.id.bt_8:
                fragmentManager.beginTransaction().replace(R.id.fl_frag_container, testFrag_3).addToBackStack(null).commit();
                break;

            case R.id.bt_9:
                fragmentManager.beginTransaction().add(R.id.fl_frag_container, testFrag_2).addToBackStack(null).commit();
                fragmentManager.beginTransaction().replace(R.id.fl_frag_container, testFrag_3).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public void onInteract(String text) {
        ToastUtil.showToast("Connection text = " + text);
    }
}
