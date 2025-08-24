package com.example.administrator.androidtest.demo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.bear.libcommon.util.CollectionUtil;
import com.bear.libcommon.util.DensityUtil;
import com.bear.libcomponent.host.ComponentFragment;
import com.bear.librv.MultiTypeAdapter;
import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;

import java.util.List;

public class TestListFragment extends ComponentFragment {

    private static final String KEY_TEST_NAME = "KEY_TEST_NAME";
    private String testName = "";

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    protected View layoutView() {
        RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(initAndGetAdapter());
        return recyclerView;
    }

    @Override
    protected void handleArgument(@NonNull Bundle bundle) {
        testName = bundle.getString(KEY_TEST_NAME);
    }

    private MultiTypeAdapter initAndGetAdapter() {
        MultiTypeAdapter adapter = new MultiTypeAdapter(getLifecycle());
        adapter.register(String.class, new TextMultiTypeDelegate());
        List<String> dataList = CollectionUtil.asListNotNull(
                "AAA", "BBB", "CCC", "DDD", "EEE", "FFF", "AAA", "BBB", "CCC", "DDD", "EEE", "FFF",
                "AAA", "BBB", "CCC", "DDD", "EEE", "FFF"
        );
        adapter.getChanger().setItems(dataList);
        return adapter;
    }

    public static TestListFragment get(String testName) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TEST_NAME, testName);
        TestListFragment testListFragment = new TestListFragment();
        testListFragment.setArguments(bundle);
        return testListFragment;
    }

    @Override
    protected ViewBinding inflateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    private static class TextMultiTypeDelegate extends MultiTypeDelegate<String, TextMultiTypeHolder> {

        @NonNull
        @Override
        protected TextMultiTypeHolder onCreateViewHolder(@NonNull View itemView) {
            return new TextMultiTypeHolder(itemView);
        }

        @Override
        protected int layoutId() {
            return -1;
        }

        @Override
        protected View itemView() {
            TextView tv = new TextView(getContext());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2Px(60));
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tv.setText("TextView");
            return tv;
        }
    }

    private static class TextMultiTypeHolder extends MultiTypeHolder<String> {

        public TextMultiTypeHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindFull(int pos, String s) {
            super.bindFull(pos, s);
            ((TextView) itemView).setText(s);
        }
    }
}
