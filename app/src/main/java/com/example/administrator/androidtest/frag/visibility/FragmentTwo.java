package com.example.administrator.androidtest.frag.visibility;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.androidtest.Base.BaseFrag;
import com.example.administrator.androidtest.Base.ComponentFrag;
import com.example.administrator.androidtest.R;

public class FragmentTwo extends ComponentFrag {
    @Override
    public int layoutId() {
        return R.layout.frag_text_2;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }
}
