package com.example.readandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTvText_1;
    private Button mBtClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_main);
//        mTvText_1 = findViewById(R.id.tv_text_1);
//        mBtClick = findViewById(R.id.bt_click);

        mBtClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test("wuyisong", 123);
            }
        });
    }

    private void test(String name, int age){

    }
}
