package com.example.administrator.androidtest.other.Frag.visibility;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bear.libcomponent.host.ComponentFragment;
import com.bear.liblog.SLog;
import com.bear.libother.page.IPage;
import com.example.administrator.androidtest.databinding.FragText2Binding;

public class FragmentTwo extends ComponentFragment<FragText2Binding> {
    @Override
    protected FragText2Binding inflateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragText2Binding.inflate(inflater, container, false);
    }

    public int pageId() {
        return IPage.FragmentTwo;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
