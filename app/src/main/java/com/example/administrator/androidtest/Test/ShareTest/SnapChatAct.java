package com.example.administrator.androidtest.Test.ShareTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Share.SnapchatShare;
import com.example.libframework.ActAndFrag.ComponentAct;

public class SnapChatAct extends ComponentAct {
    private Button mBtImageShare;
    private Button mBtVideoShare;
    @Override
    protected int layoutId() {
        return R.layout.act_snapchat_share;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mBtVideoShare = findViewById(R.id.bt_video_share);
        mBtVideoShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnapchatShare.shareVideo(mContext);
            }
        });

        mBtImageShare = findViewById(R.id.bt_image_share);
        mBtImageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnapchatShare.shareImage(mContext);
            }
        });
    }
}
