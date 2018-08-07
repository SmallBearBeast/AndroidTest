package com.example.administrator.androidtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseAct extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(layoutId() != -1){
            setContentView(layoutId());
            init();
        }
    }

    protected void init() {

    }

    protected int layoutId(){
        return -1;
    }
}
