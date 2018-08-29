package com.example.administrator.androidtest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BaseAct extends AppCompatActivity {

    private static String TAG = "BaseAct";
    private boolean foreground;
    private static int visiableCount = 0;
    public static int runningCount = 0;
    private static int aliveCount = 0;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (runningCount > 0) {
            ((BaseFrag) fragment).notifyForeground(true);
        }
    }

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aliveCount++;
        if (layoutId() != -1) {
            setContentView(layoutId());
            init();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aliveCount--;
    }

    @Override
    protected void onResume() {
        super.onResume();
        visiableCount++;
    }

    @Override
    protected void onPause() {
        super.onPause();
        visiableCount--;
    }

    protected void init() {

    }

    protected int layoutId() {
        return -1;
    }

    protected void notifyForeground(boolean fore) {
        Log.e(TAG, "class = " + getClass().getSimpleName() + "   " + "notifyForeground: fore = " + fore);
        foreground = fore;
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
}
