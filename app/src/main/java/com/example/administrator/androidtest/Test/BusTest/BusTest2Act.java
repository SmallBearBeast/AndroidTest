package com.example.administrator.androidtest.Test.BusTest;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.androidtest.R;
import com.example.libframework.ActAndFrag.ComponentAct;
import com.example.libframework.Bus.BusProvider;
import com.example.libframework.Bus.Event;

public class BusTest2Act extends ComponentAct {
    private TextView mTvTest;
    @Override
    protected int layoutId() {
        return R.layout.act_bus_test_2;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvTest = findViewById(R.id.tv_test);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_1:
                BusProvider.getLocal().sendStick(new Event("Stick", new Bundle(), 100));
                break;
        }
    }
}
