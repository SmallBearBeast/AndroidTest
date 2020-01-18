package com.example.administrator.androidtest.Test.Frag.visibility;

import com.example.administrator.androidtest.R;
import com.example.libframework.CoreUI.ComponentFrag;
import com.example.libframework.Page.IPage;

public class FragmentTwo extends ComponentFrag {
    @Override
    public int layoutId() {
        return R.layout.frag_text_2;
    }

    @Override
    public int pageId() {
        return IPage.FragmentTwo;
    }
}
