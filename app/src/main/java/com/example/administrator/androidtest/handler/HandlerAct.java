package com.example.administrator.androidtest.handler;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidtest.R;

//证明了Handler发送事件是需要排队的，后一个事件处理需要等前一个事件处理完才能启动
public class HandlerAct extends AppCompatActivity {
    private static final String TAG = "HandlerAct";
    private Handler handler = new Handler();
    private long startTime = 0;
    private TextView tvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_handler);
        tvTest = findViewById(R.id.tv_text);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_1:
                startTime = System.currentTimeMillis();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvTest.setText((System.currentTimeMillis() - startTime) * 1f / 1000 + " s");
                    }
                }, 0);
                break;

            case R.id.bt_2:
                startTime = System.currentTimeMillis();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvTest.setText((System.currentTimeMillis() - startTime) * 1f / 1000 + " s");
                    }
                }, 50);
                break;

            case R.id.bt_3:
                startTime = System.currentTimeMillis();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvTest.setText((System.currentTimeMillis() - startTime) * 1f / 1000 + " s");
                    }
                }, 100);
                break;

            case R.id.bt_4:
                for (int i = 0; i < 10; i++) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                startTime = System.currentTimeMillis();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvTest.setText((System.currentTimeMillis() - startTime) * 1f / 1000 + " s");
                    }
                }, 200);
                break;
        }
    }
}
