package com.example.administrator.androidtest.other.Frag.visibility;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bear.libcomponent.host.ComponentFragment;
import com.bear.liblog.SLog;
import com.bear.libother.page.IPage;
import com.example.administrator.androidtest.databinding.FragText4Binding;

public class FragmentFour extends ComponentFragment<FragText4Binding> {
    @Override
    protected FragText4Binding inflateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragText4Binding.inflate(inflater, container, false);
    }

    public int pageId() {
        return IPage.FragmentFour;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
