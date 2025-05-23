package com.example.administrator.androidtest.other.Frag.visibility;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ComponentFragment;
import com.bear.libcomponent.component.FragmentComponent;
import com.bear.liblog.SLog;
import com.bear.libother.page.IPage;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.FragText7Binding;

public class FragmentSeven extends ComponentFragment<FragText7Binding> {

    @Override
    public int layoutId() {
        return R.layout.frag_text_7;
    }

    @Override
    protected FragText7Binding inflateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragText7Binding.inflate(inflater, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        regFragComponent(new Component_1(getLifecycle()));
    }


    @Override
    public void onStart() {
        super.onStart();
        regFragComponent(new Component_2(getLifecycle()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public int pageId() {
        return IPage.FragmentSeven;
    }

    static class Component_1 extends FragmentComponent {
        private static final String TAG = "Component_1";

        public Component_1(Lifecycle lifecycle) {
            super(lifecycle);
        }

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

    static class Component_2 extends FragmentComponent {
        private static final String TAG = "Component_2";

        public Component_2(Lifecycle lifecycle) {
            super(lifecycle);
        }

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
