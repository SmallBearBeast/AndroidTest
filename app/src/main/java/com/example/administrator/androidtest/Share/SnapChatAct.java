package com.example.administrator.androidtest.Share;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androidtest.BaseViewSetAct;
import com.example.administrator.androidtest.R;

public class SnapChatAct extends BaseViewSetAct {
    private Button mBtSnapchatShare;
    @Override
    protected int layoutId() {
        return R.layout.act_snapchat_share;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mBtSnapchatShare = findViewById(R.id.bt_snapchat_share);
        mBtSnapchatShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnapchatShare.shareVideo(mContext);
            }
        });
    }
}
