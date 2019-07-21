package com.example.administrator.androidtest.Test.Activity;

import android.os.Bundle;

import com.example.administrator.androidtest.Common.Media.Image.ImageProvider;
import com.example.administrator.androidtest.Common.Media.Provider;
import com.example.administrator.androidtest.R;
import com.example.libframework.ActAndFrag.ComponentAct;

import java.util.List;

public class MediaProviderAct extends ComponentAct {

    private ImageProvider mImageProvider;

    @Override
    protected int layoutId() {
        return R.layout.act_media_provider;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mImageProvider = new ImageProvider(this);
        mImageProvider.fetchAlbum(new Provider.DataCallback() {

            @Override
            public void onData(List<Provider.DirInfo> infos) {

            }
        });
    }
}
