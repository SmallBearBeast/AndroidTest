package com.example.administrator.androidtest.other.Frag.visibility;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bear.libcomponent.host.ComponentFragment;
import com.bear.liblog.SLog;
import com.bear.libother.page.IPage;
import com.example.administrator.androidtest.databinding.FragText5Binding;

public class FragmentFive extends ComponentFragment<FragText5Binding> {
    @Override
    protected FragText5Binding inflateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragText5Binding.inflate(inflater, container, false);
    }

    public int pageId() {
        return IPage.FragmentFive;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
