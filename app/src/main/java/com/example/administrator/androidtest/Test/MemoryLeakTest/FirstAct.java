package com.example.administrator.androidtest.Test.MemoryLeakTest;

import android.os.Handler;
import android.view.View;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.ToastUtil;
import com.example.libframework.ActAndFrag.ComponentAct;


public class FirstAct extends ComponentAct {
    byte[] datas = new byte[1024 * 1024 * 10];
    @Override
    protected int layoutId() {
        return R.layout.act_first;
    }

    public void onClick(View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                datas[0] = '0';
                ToastUtil.showToast("Hello World");
            }
        }, 1000 * 60 * 5
        );
    }
}
