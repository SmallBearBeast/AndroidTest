package com.example.administrator.androidtest.Test.MainTest.FragTest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bear.libcomponent.ComponentFrag;
import com.example.administrator.androidtest.R;

import java.util.Random;

public class LifecycleFrag extends ComponentFrag {
    private String testTitle;
    private FragmentInteractionListener fragmentInteractionListener;
    @Override
    protected void handleArgument(@NonNull Bundle bundle) {
        testTitle = bundle.getString("title");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, testTitle + " onAttach: ");
        if (context instanceof FragmentInteractionListener) {
            fragmentInteractionListener = (FragmentInteractionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, testTitle + " onCreateView: ");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, testTitle + " onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, testTitle + " onDetach: ");
        super.onDetach();
    }

    @Override
    public void onResume() {
        Log.d(TAG, testTitle + " onResume: ");
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.d(TAG, testTitle + " onHiddenChanged: hidden = " + hidden);
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, testTitle + " onInflate: ");
        super.onInflate(context, attrs, savedInstanceState);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        Log.d(TAG, testTitle + " onAttachFragment: ");
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, testTitle + " onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, testTitle + " onStart: ");
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.d(TAG, testTitle + " onPause: ");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, testTitle + " onStop: ");
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, testTitle + " onLowMemory: ");
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, testTitle + " onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, testTitle + " onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        TextView tvTestTitle = view.findViewById(R.id.tv_frag_test_title);
        tvTestTitle.setText(testTitle);
        view.setBackgroundColor(testTitle == null ? Color.BLACK : (int)(testTitle.hashCode() * new Random().nextFloat()));
        tvTestTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentInteractionListener != null) {
                    fragmentInteractionListener.onInteract(testTitle);
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, testTitle + " onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.frag_test;
    }

    public static LifecycleFrag get(String title) {
        LifecycleFrag lifecycleFrag = new LifecycleFrag();
        Bundle argument = new Bundle();
        argument.putString("title", title);
        lifecycleFrag.setArguments(argument);
        return lifecycleFrag;
    }

    public interface FragmentInteractionListener {
        void onInteract(String text);
    }
}
