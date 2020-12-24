package com.example.administrator.androidtest.Test.OtherTest;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;
import com.example.libbase.Util.MainHandlerUtil;

import java.util.List;

/**
 后台stopService不会crash。
 后台startService超时65s后会crash(>8.0)。
 有notification的后台startService不会crash。
 调用startForegroundService必须在Service里调用startForeground，否则crash。
 android.app.RemoteServiceException: Context.startForegroundService() did not then call Service.startForeground()
 StopService建议通过Context#stopService去处理，通过startForegroundService去关闭ForegroundService没问题，如果ForegroundService变为普通Service,
 通过startForegroundService去关闭普通Service触发上面crash。
 */
public class BackgroundService extends Service {
    public static final String START = "START";
    public static final String STOP = "STOP";
    private static final String TAG = "BackgroundService";
    private NotificationManager notificationManager;
    private String notificationId = "channelId";
    private String notificationName = "channelName";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Log.d(TAG, "onStartCommand: action = " + action);
        if (STOP.equals(action)) {
            stop();
        }
        if (START.equals(action)) {
            showNotification();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void stop() {
        Log.d(TAG, "stop: ");
        stopSelf();
        stopForeground(true);
    }

    private void showNotification() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        //创建NotificationChannel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            MainHandlerUtil.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "stopForeground cancel");
                    stopForeground(true);
                }
            }, 5 * 1000);
        }
        startForeground(1,getNotification());
    }
    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.girl)
                .setContentTitle("测试服务")
                .setContentText("我正在运行");
        //设置Notification的ChannelID,否则不能正常显示
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        return builder.build();
    }

    public static void start(Context context, String action) {
        Intent intent = new Intent(context, BackgroundService.class);
        intent.setAction(action);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public static void stop(Context context, int notificationId) {
        Intent intent = new Intent(context, BackgroundService.class);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            StatusBarNotification[] statusBarNotifications = notificationManager.getActiveNotifications();
            for (StatusBarNotification statusBarNotification : statusBarNotifications) {
                if (statusBarNotification.getId() == notificationId) {
                    Log.d(TAG, "stop: exist notification");
                    start(context, STOP);
                    return;
                }
            }
        }
        context.stopService(intent);
    }

    public static void stop(Context context) {
        if (isServiceExist(context, BackgroundService.class)) {
            Log.d(TAG, "stop: BackgroundService exist");
            start(context, STOP);
        }
    }

    private static boolean isServiceExist(Context context, Class<? extends Service> serviceClass) {
        String className = serviceClass.getName();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(Integer.MAX_VALUE);
        int myUid = android.os.Process.myUid();
        for (ActivityManager.RunningServiceInfo runningServiceInfo : serviceList) {
            if (runningServiceInfo.uid == myUid && runningServiceInfo.service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }
}
