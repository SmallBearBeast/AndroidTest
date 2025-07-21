package com.example.administrator.androidtest.demo.optdemo.bootoptdemo;

import android.app.Application;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.bear.libcommon.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.PathClassLoader;

public class MonitorClassLoader extends PathClassLoader {
    private static final String TAG = "MonitorClassLoader";
    private boolean onlyMainThread = false;
    private static Map<String, Long> classLoadTimeMap = new HashMap<>();

    public MonitorClassLoader(String dexPath, ClassLoader parent, boolean onlyMainThread) {
        super(dexPath, parent);
        this.onlyMainThread = onlyMainThread;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        long begin = SystemClock.elapsedRealtimeNanos();
        if (onlyMainThread && Looper.getMainLooper() != Looper.myLooper()) {
            return super.loadClass(name, resolve);
        }
        Class<?> clz = super.loadClass(name, resolve);
        long cost = SystemClock.elapsedRealtimeNanos() - begin;
        classLoadTimeMap.put(clz.getName(), cost/1000);
//        Log.e(TAG, "加载class = " + clz + ", 耗时cost = " + cost/1000 + "微秒, 线程ID = " + Thread.currentThread().getId());
        return clz;
    }

    /*
        核心是在application的ClassLoader与parentClassLoader之间插入MonitorClassLoader，因为不好去hook替换到原有的application的ClassLoader和其他地方的ClassLoader。
        更全面的方法是生成一个application的ClassLoader的代理对象，在进行插入到parent，这样不会修改原有的加载逻辑。
        步骤：
        1. 反射获取原始 pathClassLoader 的 pathList，因为需要用到DexPathList去findClass。
        2. 创建MonitorClassLoader，并反射设置 正确的 pathList。所以这里在MonitorClassLoader这一层已经实现了类的加载与获取。
        3. 反射替换 原始pathClassLoader的 parent指向 MonitorClassLoader实例
     */
    public static void hook(Application application, boolean onlyMainThread) {
        ClassLoader appClassLoader = application.getClassLoader();
        try {
            MonitorClassLoader monitorClassLoader = new MonitorClassLoader("", appClassLoader.getParent(), onlyMainThread);
            Field pathListField = ReflectUtil.findField(BaseDexClassLoader.class, "pathList");
            Object pathList = pathListField.get(appClassLoader);
            pathListField.set(monitorClassLoader, pathList);

            Field parentField = ReflectUtil.findField(ClassLoader.class, "parent");
            parentField.set(appClassLoader, monitorClassLoader);
        } catch (Exception e) {
            Log.e(TAG, "hook fail: message = " + e.getMessage());
        }
    }

    public static void printLoadTimeInfo() {
        List<Map.Entry<String, Long>> entryList = new ArrayList<>(classLoadTimeMap.entrySet());
        Collections.sort(entryList, (first, second) -> (int) (second.getValue() - first.getValue()));
        StringBuilder builder = new StringBuilder();
        StringBuilder timeBuilder = new StringBuilder();
        long totalTime = 0;
        for (int i = 0; i < entryList.size(); i++) {
            Map.Entry<String, Long> entry = entryList.get(i);
            builder.append("key = ").append(entry.getKey());
            builder.append(", value = ").append(entry.getValue());
            builder.append("\n");
            if (i != 0 && (i % 50) == 0) {
                timeBuilder.append("i = ").append(i);
                timeBuilder.append(", total time = ").append(totalTime);
                timeBuilder.append("\n");
            }
            totalTime = totalTime + entry.getValue();
            Log.e(TAG, "加载class = " + entry.getKey() + ", 耗时cost = " + entry.getValue() + "微秒");
        }
        timeBuilder.append("i = ").append(entryList.size() - 1);
        timeBuilder.append(", total time = ").append(totalTime);
//        Log.e(TAG, builder.toString());
        Log.e(TAG, timeBuilder.toString());
    }
}
