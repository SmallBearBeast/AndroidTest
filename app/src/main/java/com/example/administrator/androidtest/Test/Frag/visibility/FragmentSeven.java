package com.example.administrator.androidtest.Test.Frag.visibility;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcomponent.ComponentFrag;
import com.bear.libcomponent.ViewComponent;
import com.example.administrator.androidtest.R;
import com.example.libframework.Page.IPage;
import com.example.liblog.SLog;

public class FragmentSeven extends ComponentFrag {

    @Override
    public int layoutId() {
        return R.layout.frag_text_7;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        regComponent(new Component_1());
    }


    @Override
    public void onStart() {
        super.onStart();
        regComponent(new Component_2());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public int pageId() {
        return IPage.FragmentSeven;
    }

    static class Component_1 extends ViewComponent<FragmentSeven> {
        private static final String TAG = "Component_1";
        @Override
        public void onCreate() {
            SLog.d(TAG, "onCreate: ");
            super.onCreate();
        }

        @Override
        protected void onStart() {
            SLog.d(TAG, "onStart: ");
            super.onStart();
        }

        @Override
        public void onDestroy() {
            SLog.d(TAG, "onDestroy: ");
            super.onDestroy();
        }
    }

    static class Component_2 extends ViewComponent<FragmentSeven> {
        private static final String TAG = "Component_2";
        @Override
        public void onCreate() {
            SLog.d(TAG, "onCreate: ");
            super.onCreate();
        }

        @Override
        protected void onStart() {
            SLog.d(TAG, "onStart: ");
            super.onStart();
        }

        @Override
        public void onDestroy() {
            SLog.d(TAG, "onDestroy: ");
            super.onDestroy();
        }
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
