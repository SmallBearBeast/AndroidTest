package com.example.administrator.androidtest.demo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.ARouterTest.ARouterTestComponent;
import com.example.administrator.androidtest.demo.AspectTest.AspectJTestComponent;
import com.example.administrator.androidtest.demo.BizDemo.BizDemoComponent;
import com.example.administrator.androidtest.demo.ComponentDemo.DemoComponent;
import com.example.administrator.androidtest.demo.FlutterTest.FlutterTestComponent;
import com.example.administrator.androidtest.demo.LibraryDemo.LibraryDemoComponent;
import com.example.administrator.androidtest.demo.OptTest.AnrTest.AnrTestComponent;
import com.example.administrator.androidtest.demo.OptTest.BootOptTest.BootTaskManager;
import com.example.administrator.androidtest.demo.OptTest.BootOptTest.MonitorClassLoader;
import com.example.administrator.androidtest.demo.BottomSheetTest.BottomSheetTestComponent;
import com.example.administrator.androidtest.demo.BusTest.BusTestComponent;
import com.example.administrator.androidtest.demo.DialogTest.DialogTestComponent;
import com.example.administrator.androidtest.demo.FloatServiceTest.FloatServiceTestComponent;
import com.example.administrator.androidtest.demo.FragTest.FragLifecycleTestComponent;
import com.example.administrator.androidtest.demo.KVCompareTest.KVCompareTestComponent;
import com.example.administrator.androidtest.demo.MediaDemo.MediaDemoComponent;
import com.example.administrator.androidtest.demo.OtherDemo.OtherDemoComponent;
import com.example.administrator.androidtest.demo.ScreenTest.ScreenTestComponent;
import com.example.administrator.androidtest.demo.SpAndMMKVDemo.SpAndMMKVDemoComponent;
import com.example.administrator.androidtest.demo.StartBgServiceDemo.StartBgServiceDemoComponent;
import com.example.administrator.androidtest.demo.ViewDemo.ViewDemoComponent;
import com.example.administrator.androidtest.demo.widgetDemo.WidgetDemoComponent;
import com.bear.libcommon.util.MainHandlerUtil;

public class MainActivity extends ComponentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BootTaskManager.getInstance().waitCountDown();
        BootTaskManager.getInstance().logColdEndUp();
        BootTaskManager.getInstance().logWarmStartUp();
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: enter");
        regActComponent(new BizDemoComponent(getLifecycle()));
        regActComponent(new LibraryDemoComponent(getLifecycle()));
        regActComponent(new AnrTestComponent(getLifecycle()));
        regActComponent(new OtherDemoComponent(getLifecycle()));
        regActComponent(new MediaDemoComponent(getLifecycle()));
        regActComponent(new WidgetDemoComponent(getLifecycle()));
        regActComponent(new ViewDemoComponent(getLifecycle()));
        regActComponent(new StartBgServiceDemoComponent(getLifecycle()));
        regActComponent(new SpAndMMKVDemoComponent(getLifecycle()));
        regActComponent(new FloatServiceTestComponent(getLifecycle()));
        regActComponent(new ScreenTestComponent(getLifecycle()));
        regActComponent(new KVCompareTestComponent(getLifecycle()));
        regActComponent(new FragLifecycleTestComponent(getLifecycle()));
        regActComponent(new BottomSheetTestComponent(getLifecycle()));
        regActComponent(new BusTestComponent(getLifecycle()));
        regActComponent(new AspectJTestComponent(getLifecycle()));
        regActComponent(new DialogTestComponent(getLifecycle()));
        regActComponent(new ARouterTestComponent(getLifecycle()));
        regActComponent(new FlutterTestComponent(getLifecycle()));
        regActComponent(new DemoComponent(getLifecycle()));
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
