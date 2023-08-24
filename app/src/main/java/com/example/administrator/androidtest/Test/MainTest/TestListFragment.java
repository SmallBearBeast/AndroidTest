package com.example.administrator.androidtest.Test.MainTest;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bear.libcomponent.component.ComponentFrag;
import com.bear.librv.VHAdapter;
import com.bear.librv.VHBridge;
import com.bear.librv.VHolder;
import com.example.libbase.Util.CollectionUtil;
import com.example.libbase.Util.DensityUtil;

import java.util.List;

public class TestListFragment extends ComponentFrag {

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

    private VHAdapter<TextVHolder> initAndGetAdapter() {
        VHAdapter<TextVHolder> vhAdapter = new VHAdapter<>(getLifecycle());
        vhAdapter.register(new TextVHBridge(), String.class);
        List<String> dataList = CollectionUtil.asListNotNull(
                "AAA", "BBB", "CCC", "DDD", "EEE", "FFF", "AAA", "BBB", "CCC", "DDD", "EEE", "FFF",
                "AAA", "BBB", "CCC", "DDD", "EEE", "FFF"
        );
        vhAdapter.getDataManager().setData(dataList);
        return vhAdapter;
    }

    public static TestListFragment get(String testName) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TEST_NAME, testName);
        TestListFragment testListFragment = new TestListFragment();
        testListFragment.setArguments(bundle);
        return testListFragment;
    }

    private static class TextVHBridge extends VHBridge<TextVHolder> {

        @NonNull
        @Override
        protected TextVHolder onCreateViewHolder(@NonNull View itemView) {
            return new TextVHolder(itemView);
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

    private static class TextVHolder extends VHolder<String> {

        public TextVHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindFull(int pos, String s) {
            super.bindFull(pos, s);
            ((TextView) itemView).setText(s);
        }
    }
}
