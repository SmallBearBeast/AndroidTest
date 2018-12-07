package com.example.administrator.androidtest.Share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.util.List;
import java.util.Locale;

public class ShareUtil {
    private static final int REQUEST_CODE = 2001;

    public static void shareTextToApp(Activity activity, IntentShare intentShare, String packageName, String activityName){
        Intent it = checkShareToApp(activity.getApplicationContext(), IntentShare.TYPE_TEXT, packageName, activityName);
        if (it != null) {
            it.putExtra(Intent.EXTRA_TEXT, intentShare.getText());
            activity.startActivityForResult(Intent.createChooser(it, ""), REQUEST_CODE);
        } else {
            // TODO: 2018/12/6 打印toast
        }
    }

    public static void shareTextToApp(Activity activity, IntentShare intentShare, String packageName){
        shareTextToApp(activity, intentShare, packageName, null);
    }


    public static void shareImageTextToApp(Activity activity, IntentShare intentShare, String packageName){
        shareImageTextToApp(activity, intentShare, packageName, null);
    }

    public static void shareImageTextToApp(Activity activity, IntentShare intentShare, String packageName, String activityName){
        Intent it = checkShareToApp(activity.getApplicationContext(), IntentShare.TYPE_IMAGE, packageName, activityName);
        if (it != null) {
            it.putExtra(Intent.EXTRA_TEXT, intentShare.getText());
            it.putExtra(Intent.EXTRA_STREAM, intentShare.getImageUri());
            activity.startActivityForResult(Intent.createChooser(it, ""), REQUEST_CODE);
        } else {
            // TODO: 2018/12/6 打印toast
        }
    }

    public static void shareVideoTextToApp(Activity activity, IntentShare intentShare, String packageName){
        shareVideoTextToApp(activity, intentShare, packageName, null);
    }

    public static void shareVideoTextToApp(Activity activity, IntentShare intentShare, String packageName, String activityName){
        Intent it = checkShareToApp(activity.getApplicationContext(), IntentShare.TYPE_VIDEO, packageName, activityName);
        if (it != null) {
            it.putExtra(Intent.EXTRA_TEXT, intentShare.getText());
            it.putExtra(Intent.EXTRA_STREAM, intentShare.getVideoUri());
            activity.startActivityForResult(Intent.createChooser(it, ""), REQUEST_CODE);
        } else {
            // TODO: 2018/12/6 打印toast
        }
    }

    public static Intent checkShareToApp(Context context, String type, String packageName) {
        return checkShareToApp(context, type, packageName, null);
    }

    /**
     * 判断是否可以分享指定资源类型到其他应用，yes返回对应Intent，no返回null
     * type是不同的资源类型，packageName是对应包名，activityName是当应用存在多个分享页面作为进一步区分判断，
     * 可以截取页面名字的某一部分作为特征判断，一般为null。
     */
    public static Intent checkShareToApp(Context context, String type, String packageName, String activityName) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(type);
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if(resInfo != null && !resInfo.isEmpty()){
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            for (ResolveInfo info : resInfo) {
                ActivityInfo activityInfo = info.activityInfo;
                boolean isMatch = activityInfo.packageName.toLowerCase(Locale.ENGLISH).equals(packageName);
                isMatch = isMatch && (activityName == null || activityInfo.name.contains(activityName));
                if (isMatch) {
                    shareIntent.setPackage(activityInfo.packageName);
                    shareIntent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                    return shareIntent;
                }
            }
        }
        return null;
    }
}
