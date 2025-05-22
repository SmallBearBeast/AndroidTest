package com.example.administrator.androidtest.other.Frag.visibility;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bear.libcomponent.component.ComponentFragment;
import com.bear.liblog.SLog;
import com.bear.libother.page.IPage;
import com.example.administrator.androidtest.databinding.FragText6Binding;

public class FragmentSix extends ComponentFragment<FragText6Binding> {

    @Override
    protected FragText6Binding inflateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragText6Binding.inflate(inflater, container, false);
    }

    public int pageId() {
        return IPage.FragmentSix;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
