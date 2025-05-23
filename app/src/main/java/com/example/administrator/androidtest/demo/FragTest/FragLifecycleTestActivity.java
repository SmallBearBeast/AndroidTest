package com.example.administrator.androidtest.demo.FragTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bear.libcommon.util.ToastUtil;
import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActFragTestBinding;

public class FragLifecycleTestActivity extends ComponentActivity<ActFragTestBinding> implements LifecycleFragment.FragmentInteractionListener {
    private LifecycleFragment lifecycleFrag_1 = LifecycleFragment.get("testFrag_1");
    private LifecycleFragment lifecycleFrag_2 = LifecycleFragment.get("testFrag_2");
    private LifecycleFragment lifecycleFrag_3 = LifecycleFragment.get("testFrag_3");
    private LifecycleFragment lifecycleFrag_4 = LifecycleFragment.get("testFrag_4");
    private LifecycleFragment lifecycleFrag_5 = LifecycleFragment.get("testFrag_5");

    private FragmentManager fragmentManager;

    @Override
    protected ActFragTestBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActFragTestBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragContainerView, lifecycleFrag_1).commit();
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
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTestFrag2Button:
                // 重复add会抛出Fragment already added异常
                fragmentManager.beginTransaction().add(R.id.fragContainerView, lifecycleFrag_2).commit();
                break;

            case R.id.removeTestFrag2Button:
                fragmentManager.beginTransaction().remove(lifecycleFrag_2).commit();
                break;

            case R.id.showTestFrag2Button:
                fragmentManager.beginTransaction().show(lifecycleFrag_2).commit();
                break;

            case R.id.hideTestFrag2Button:
                fragmentManager.beginTransaction().hide(lifecycleFrag_2).commit();
                break;

            case R.id.attachTestFrag2Button:
                fragmentManager.beginTransaction().attach(lifecycleFrag_2).commit();
                break;

            case R.id.detachTestFrag2Button:
                fragmentManager.beginTransaction().detach(lifecycleFrag_2).commit();
                break;

            case R.id.replaceTestFrag3Button:
                fragmentManager.beginTransaction().replace(R.id.fragContainerView, lifecycleFrag_3).commit();
                break;

            case R.id.bt_8:
                fragmentManager.beginTransaction().replace(R.id.fragContainerView, lifecycleFrag_3).addToBackStack(null).commit();
                break;

            case R.id.bt_9:
                fragmentManager.beginTransaction().add(R.id.fragContainerView, lifecycleFrag_2).addToBackStack(null).commit();
                fragmentManager.beginTransaction().replace(R.id.fragContainerView, lifecycleFrag_3).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public void onInteract(String text) {
        ToastUtil.showToast("Connection text = " + text);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, FragLifecycleTestActivity.class));
    }
}
