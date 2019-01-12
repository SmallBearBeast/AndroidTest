package com.example.administrator.androidtest.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/*
    相同类型fragment复用时候需要一个fragmentId来区分
 */
public abstract class BaseMulFrag extends BaseFrag {
    public static final String KEY_ID = "key_id";
    protected int fragmentId;

    public Bundle buildArguments(int id){
        fragmentId = id;
        Bundle bundle = buildArguments();
        bundle.putInt(KEY_ID, fragmentId);
        return bundle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            fragmentId = getArguments().getInt(KEY_ID, 0);
        }
    }
}
