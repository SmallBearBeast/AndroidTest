package com.example.administrator.androidtest;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BaseAct extends AppCompatActivity {

    private static final String TAG = "BaseAct";
    private static final int Permission_Request_Code = 1;
    private static int runningCount = 0;

    private boolean foreground;

    @Override
    protected void onStart() {
        super.onStart();
        runningCount++;
        if (runningCount == 1) {
            notifyForeground(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        runningCount--;
        if (runningCount <= 0) {
            notifyForeground(false);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void init() {

    }

    protected void init(Bundle savedInstanceState) {

    }

    protected int layoutId() {
        return -1;
    }

    protected void onNotifyForeground(boolean fore) {
        Log.e(TAG, "class = " + getClass().getSimpleName() + "   " + "onNotifyForeground: fore = " + fore);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof BaseFrag){
            ((BaseFrag)fragment).notifyForeground(foreground);
        }
    }

    protected void notifyForeground(boolean fore) {
        foreground = fore;
        onNotifyForeground(fore);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                ((BaseFrag) frag).notifyForeground(fore);
            }
        }
    }

    public boolean isForeground() {
        return foreground;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void askPermissions(String[] permissions){
        requestPermissions(permissions, Permission_Request_Code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
