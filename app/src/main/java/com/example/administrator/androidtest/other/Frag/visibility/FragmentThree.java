package com.example.administrator.androidtest.other.Frag.visibility;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bear.libcomponent.component.ComponentFragment;
import com.bear.liblog.SLog;
import com.bear.libother.page.IPage;
import com.example.administrator.androidtest.databinding.FragText3Binding;

public class FragmentThree extends ComponentFragment<FragText3Binding> {
    @Override
    protected FragText3Binding inflateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragText3Binding.inflate(inflater, container, false);
    }

    public int pageId() {
        return IPage.FragmentThree;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
