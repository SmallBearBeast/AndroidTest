package com.example.administrator.androidtest.frag.visibility;

import android.os.Bundle;

import com.example.administrator.androidtest.Base.ActAndFrag.ComponentFrag;
import com.example.administrator.androidtest.Common.Page.IPage;
import com.example.administrator.androidtest.Common.Page.Page;
import com.example.administrator.androidtest.R;

public class FragmentThree extends ComponentFrag {


    @Override
    public int layoutId() {
        return R.layout.frag_text_3;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int pageId() {
        return IPage.FragmentThree;
    }
}
