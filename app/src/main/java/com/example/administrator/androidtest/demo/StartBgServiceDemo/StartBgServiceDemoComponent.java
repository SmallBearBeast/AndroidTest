package com.example.administrator.androidtest.demo.StartBgServiceDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.BackgroundService;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class StartBgServiceDemoComponent extends TestActivityComponent {

    public StartBgServiceDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        getBinding().startBgServiceButton.setOnClickListener(this);
        getBinding().stopBgServiceButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.startBgServiceButton:
                testStartBgService();
                break;

            case R.id.stopBgServiceButton:
                testStopBgService();
                break;
        }
    }

    private void testStopBgService() {
        //                BackgroundService.stopByReceiver(this);
        BackgroundService.stop(getContext(), BackgroundService.getNotificationId());
//                MainHandlerUtil.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        BackgroundService.stop(OtherTestAct.this, 1);
//                        BackgroundService.start(OtherTestAct.this, BackgroundService.STOP);
//                        stopService(new Intent(OtherTestAct.this, BackgroundService.class));
//
//                    }
//                }, 5* 1000);
    }

    private void testStartBgService() {
        BackgroundService.start(getContext(), BackgroundService.START);
//                MainHandlerUtil.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        BackgroundService.start(OtherTestAct.this, BackgroundService.START);
//                        BackgroundService.start(OtherTestAct.this, BackgroundService.STOP);
//                        BackgroundService.stopDirectly(OtherTestAct.this);
//                        MainHandlerUtil.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                BackgroundService.stopDirectly(OtherTestAct.this);
//                            }
//                        }, 100);
//                    }
//                }, 5 * 1000);
    }
}
