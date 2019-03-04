package com.example.administrator.androidtest.Net;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;
import com.example.administrator.androidtest.Net.Okhttp.OkCallback;
import com.example.administrator.androidtest.Net.Okhttp.OkHelper;
import com.example.administrator.androidtest.R;

public class OkhttpAct extends ComponentAct {

    private TextView mTvContent;
    @Override
    protected int layoutId() {
        return R.layout.act_okhttp;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvContent = findViewById(R.id.tv_content);
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_get:
                break;

            case R.id.bt_post:
                break;

            case R.id.bt_download:
                break;

            case R.id.bt_upload:
                break;
        }
    }


    private void testGet(String url){
        OkHelper.getInstance().getMethod(url, new OkCallback<String>(String.class){
            @Override
            protected void handleErrCode(int errCode) {
                super.handleErrCode(errCode);
            }

            @Override
            protected void onSuccess(String data) {
                super.onSuccess(data);
            }

            @Override
            protected void onFail() {
                super.onFail();
            }
        });
    }

}
