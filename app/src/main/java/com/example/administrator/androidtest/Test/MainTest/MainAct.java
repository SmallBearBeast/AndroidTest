package com.example.administrator.androidtest.Test.MainTest;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.ARouterTest.ARouterTestComponent;
import com.example.administrator.androidtest.Test.MainTest.AspectTest.AspectJTestComponent;
import com.example.administrator.androidtest.Test.MainTest.BizDemo.BizDemoComponent;
import com.example.administrator.androidtest.Test.MainTest.ComponentDemo.DemoComponent;
import com.example.administrator.androidtest.Test.MainTest.FlutterTest.FlutterTestComponent;
import com.example.administrator.androidtest.Test.MainTest.LibraryDemo.LibraryDemoComponent;
import com.example.administrator.androidtest.Test.MainTest.OptTest.AnrTest.AnrTestComponent;
import com.example.administrator.androidtest.Test.MainTest.OptTest.BootOptTest.BootTaskManager;
import com.example.administrator.androidtest.Test.MainTest.OptTest.BootOptTest.MonitorClassLoader;
import com.example.administrator.androidtest.Test.MainTest.BottomSheetTest.BottomSheetTestComponent;
import com.example.administrator.androidtest.Test.MainTest.BusTest.BusTestComponent;
import com.example.administrator.androidtest.Test.MainTest.DialogTest.DialogTestComponent;
import com.example.administrator.androidtest.Test.MainTest.FloatServiceTest.FloatServiceTestComponent;
import com.example.administrator.androidtest.Test.MainTest.FragTest.FragLifecycleTestComponent;
import com.example.administrator.androidtest.Test.MainTest.KVCompareTest.KVCompareTestComponent;
import com.example.administrator.androidtest.Test.MainTest.MediaTest.MediaTestComponent;
import com.example.administrator.androidtest.Test.MainTest.OkHttpTest.OkHttpTestComponent;
import com.example.administrator.androidtest.Test.MainTest.OtherTest.OtherTestComponent;
import com.example.administrator.androidtest.Test.MainTest.ScreenTest.ScreenTestComponent;
import com.example.administrator.androidtest.Test.MainTest.SpAndMMKVDemo.SpAndMMKVDemoComponent;
import com.example.administrator.androidtest.Test.MainTest.StartBgServiceDemo.StartBgServiceDemoComponent;
import com.example.administrator.androidtest.Test.MainTest.ViewDemo.ViewDemoComponent;
import com.example.administrator.androidtest.Test.MainTest.WidgetDemo.WidgetDemoComponent;
import com.example.libbase.Util.MainHandlerUtil;

public class MainAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BootTaskManager.getInstance().waitCountDown();
        BootTaskManager.getInstance().logColdEndUp();
        BootTaskManager.getInstance().logWarmStartUp();
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: enter");
        regActComponent(new BizDemoComponent());
        regActComponent(new LibraryDemoComponent());
        regActComponent(new AnrTestComponent());
        regActComponent(new OtherTestComponent());
        regActComponent(new MediaTestComponent());
        regActComponent(new OkHttpTestComponent());
        regActComponent(new WidgetDemoComponent());
        regActComponent(new ViewDemoComponent());
        regActComponent(new StartBgServiceDemoComponent());
        regActComponent(new SpAndMMKVDemoComponent());
        regActComponent(new FloatServiceTestComponent());
        regActComponent(new ScreenTestComponent());
        regActComponent(new KVCompareTestComponent());
        regActComponent(new FragLifecycleTestComponent());
        regActComponent(new BottomSheetTestComponent());
        regActComponent(new BusTestComponent());
        regActComponent(new AspectJTestComponent());
        regActComponent(new DialogTestComponent());
        regActComponent(new ARouterTestComponent());
        regActComponent(new FlutterTestComponent());
        regActComponent(new DemoComponent());
        MainHandlerUtil.postDelayed(MonitorClassLoader::printLoadTimeInfo, 2000);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_main_demo_list;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            BootTaskManager.getInstance().logWarmEndUp();
        }
        super.onWindowFocusChanged(hasFocus);
    }
}
