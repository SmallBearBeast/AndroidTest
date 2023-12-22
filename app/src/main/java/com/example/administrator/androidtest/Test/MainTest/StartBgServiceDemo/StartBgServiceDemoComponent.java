package com.example.administrator.androidtest.Test.MainTest.StartBgServiceDemo;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.BackgroundService;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class StartBgServiceDemoComponent extends TestComponent {

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.startBgServiceButton, R.id.stopBgServiceButton);
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
