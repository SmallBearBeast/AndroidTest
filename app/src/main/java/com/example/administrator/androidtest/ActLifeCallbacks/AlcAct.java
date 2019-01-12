package com.example.administrator.androidtest.ActLifeCallbacks;

import android.content.Intent;
import android.view.View;

import com.example.administrator.androidtest.Base.BaseAct;
import com.example.administrator.androidtest.R;

public class AlcAct extends BaseAct{

    @Override
    protected int layoutId() {
        return R.layout.act_alc;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_1:
                startActivity(new Intent(this, AlcOneAct.class));
                break;
            case R.id.bt_2:
                startActivity(new Intent(this, AlcTwoAct.class));
                break;
        }
    }

}
