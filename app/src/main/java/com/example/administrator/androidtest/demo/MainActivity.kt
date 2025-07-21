package com.example.administrator.androidtest.demo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.bear.libcommon.util.MainHandlerUtil
import com.bear.libcomponent.component.ComponentActivity
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.databinding.ActMainDemoListBinding
import com.example.administrator.androidtest.demo.ARouterTest.ARouterTestComponent
import com.example.administrator.androidtest.demo.AspectTest.AspectJTestComponent
import com.example.administrator.androidtest.demo.BizDemo.BizDemoComponent
import com.example.administrator.androidtest.demo.BottomSheetTest.BottomSheetTestComponent
import com.example.administrator.androidtest.demo.BusTest.BusTestComponent
import com.example.administrator.androidtest.demo.ComponentDemo.DemoComponent
import com.example.administrator.androidtest.demo.DialogTest.DialogTestComponent
import com.example.administrator.androidtest.demo.FloatServiceTest.FloatServiceTestComponent
import com.example.administrator.androidtest.demo.FlutterTest.FlutterTestComponent
import com.example.administrator.androidtest.demo.FragTest.FragLifecycleTestComponent
import com.example.administrator.androidtest.demo.KVCompareTest.KVCompareTestComponent
import com.example.administrator.androidtest.demo.LibraryDemo.LibraryDemoComponent
import com.example.administrator.androidtest.demo.MediaDemo.MediaDemoComponent
import com.example.administrator.androidtest.demo.OtherDemo.OtherDemoComponent
import com.example.administrator.androidtest.demo.ScreenTest.ScreenTestComponent
import com.example.administrator.androidtest.demo.SpAndMMKVDemo.SpAndMMKVDemoComponent
import com.example.administrator.androidtest.demo.StartBgServiceDemo.StartBgServiceDemoComponent
import com.example.administrator.androidtest.demo.ViewDemo.ViewDemoComponent
import com.example.administrator.androidtest.demo.optdemo.anrdemo.AnrTestComponent
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.AppLaunchTracer
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.BootTaskManager
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.MonitorClassLoader
import com.example.administrator.androidtest.demo.widgetDemo.WidgetDemoComponent

class MainActivity : ComponentActivity<ActMainDemoListBinding?>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        BootTaskManager.getInstance().waitCountDown()
        AppLaunchTracer.markActivityCreate()
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: enter")
        regActComponent(BizDemoComponent(lifecycle))
        regActComponent(LibraryDemoComponent(lifecycle))
        regActComponent(AnrTestComponent(lifecycle))
        regActComponent(OtherDemoComponent(lifecycle))
        regActComponent(MediaDemoComponent(lifecycle))
        regActComponent(WidgetDemoComponent(lifecycle))
        regActComponent(ViewDemoComponent(lifecycle))
        regActComponent(StartBgServiceDemoComponent(lifecycle))
        regActComponent(SpAndMMKVDemoComponent(lifecycle))
        regActComponent(FloatServiceTestComponent(lifecycle))
        regActComponent(ScreenTestComponent(lifecycle))
        regActComponent(KVCompareTestComponent(lifecycle))
        regActComponent(FragLifecycleTestComponent(lifecycle))
        regActComponent(BottomSheetTestComponent(lifecycle))
        regActComponent(BusTestComponent(lifecycle))
        regActComponent(AspectJTestComponent(lifecycle))
        regActComponent(DialogTestComponent(lifecycle))
        regActComponent(ARouterTestComponent(lifecycle))
        regActComponent(FlutterTestComponent(lifecycle))
        regActComponent(DemoComponent(lifecycle))
        MainHandlerUtil.postDelayed({ MonitorClassLoader.printLoadTimeInfo() }, 2000)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActMainDemoListBinding {
        return ActMainDemoListBinding.inflate(inflater)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            AppLaunchTracer.markFirstFrameDraw()
        }
        super.onWindowFocusChanged(hasFocus)
    }
}
