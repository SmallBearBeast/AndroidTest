package com.example.administrator.androidtest.Share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import com.example.libbase.Util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ShareUtil {
    private static final int REQUEST_CODE = 2001;

    public static void shareTextToOther(Activity activity, IntentShare intentShare){
        Intent it = checkShareToOther(activity.getApplicationContext(), IntentShare.TYPE_TEXT);
        if(it != null){
            it.putExtra(Intent.EXTRA_TEXT, intentShare.getText());
            activity.startActivityForResult(Intent.createChooser(it, "分享到其他"), REQUEST_CODE);
        }else {
            ToastUtil.showToast("分享文本失败");
        }
    }

    public static void shareImageTextToOther(Activity activity, IntentShare intentShare){
        Intent it = checkShareToOther(activity.getApplicationContext(), IntentShare.TYPE_IMAGE);
        if(it != null){
            it.putExtra(Intent.EXTRA_TEXT, intentShare.getText());
            it.putExtra(Intent.EXTRA_STREAM, intentShare.getImageUri());
            activity.startActivityForResult(Intent.createChooser(it, "分享到其他"), REQUEST_CODE);
        }else {
            ToastUtil.showToast("分享图片失败");
        }
    }

    public static void shareVideoTextToOther(Activity activity, IntentShare intentShare){
        Intent it = checkShareToOther(activity.getApplicationContext(), IntentShare.TYPE_VIDEO);
        if(it != null){
            it.putExtra(Intent.EXTRA_TEXT, intentShare.getText());
            it.putExtra(Intent.EXTRA_STREAM, intentShare.getVideoUri());
            activity.startActivityForResult(Intent.createChooser(it, "分享到其他"), REQUEST_CODE);
        }else {
            ToastUtil.showToast("分享视频失败");
        }
    }

    private static Intent checkShareToOther(Context context, String type) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(type);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if(resInfo != null && !resInfo.isEmpty()){
            return shareIntent;
        }
        return null;
    }

    /**
     * 分享文字到指定包名的app
     */
    public static void shareTextToApp(Activity activity, IntentShare intentShare, String packageName, String activityName){
        Intent it = checkShareToApp(activity.getApplicationContext(), IntentShare.TYPE_TEXT, packageName, activityName);
        if (it != null) {
            it.putExtra(Intent.EXTRA_TEXT, intentShare.getText());
            activity.startActivityForResult(Intent.createChooser(it, "Sharing.."), REQUEST_CODE);
        } else {
            ToastUtil.showToast("分享文本失败");
        }
    }

    public static void shareTextToApp(Activity activity, IntentShare intentShare, String packageName){
        shareTextToApp(activity, intentShare, packageName, null);
    }


    public static void shareImageTextToApp(Activity activity, IntentShare intentShare, String packageName){
        shareImageTextToApp(activity, intentShare, packageName, null);
    }

    /**
     * 分享图片和文字到指定包名的app，有的应用不能同时接受图片和文字，只能接受图片
     */
    public static void shareImageTextToApp(Activity activity, IntentShare intentShare, String packageName, String activityName){
        Intent it = checkShareToApp(activity.getApplicationContext(), IntentShare.TYPE_IMAGE, packageName, activityName);
        if (it != null && checkShareSize(IntentShare.TYPE_IMAGE, intentShare.getImageUri(), packageName)) {
            it.putExtra(Intent.EXTRA_TEXT, intentShare.getText());
            it.putExtra(Intent.EXTRA_STREAM, intentShare.getImageUri());
            activity.startActivityForResult(Intent.createChooser(it, "Sharing.."), REQUEST_CODE);
        } else {
            ToastUtil.showToast("分享图片失败");
        }
    }

    public static void shareVideoTextToApp(Activity activity, IntentShare intentShare, String packageName){
        shareVideoTextToApp(activity, intentShare, packageName, null);
    }

    /**
     * 分享视频和文字到指定包名的app，有的应用不能同时接受视频和文字，只能接受视频
     */
    public static void shareVideoTextToApp(Activity activity, IntentShare intentShare, String packageName, String activityName){
        Intent it = checkShareToApp(activity.getApplicationContext(), IntentShare.TYPE_VIDEO, packageName, activityName);
        if (it != null && checkShareSize(IntentShare.TYPE_VIDEO, intentShare.getVideoUri(), packageName)) {
            it.putExtra(Intent.EXTRA_TEXT, intentShare.getText());
            it.putExtra(Intent.EXTRA_STREAM, intentShare.getVideoUri());
            activity.startActivityForResult(Intent.createChooser(it, "Sharing.."), REQUEST_CODE);
        } else {
            ToastUtil.showToast("分享视频失败");
        }
    }

    public static void shareFileTextToApp(Activity activity, IntentShare intentShare, String packageName){
        shareFileTextToApp(activity, intentShare, packageName, null);
    }

    public static void shareFileTextToApp(Activity activity, IntentShare intentShare, String packageName, String activityName){
        Intent it = checkShareToApp(activity.getApplicationContext(), IntentShare.TYPE_ALL, packageName, activityName);
        if (it != null && checkShareSize(IntentShare.TYPE_ALL, intentShare.getVideoUri(), packageName)) {
            it.putExtra(Intent.EXTRA_TEXT, intentShare.getText());
            it.putExtra(Intent.EXTRA_STREAM, intentShare.getFileUri());
            activity.startActivityForResult(Intent.createChooser(it, "Sharing.."), REQUEST_CODE);
        } else {
            ToastUtil.showToast("分享视频失败");
        }
    }

    public static void shareMulFileTextToApp(Activity activity, IntentShare intentShare, String packageName){
        shareMulFileTextToApp(activity, intentShare, packageName, null);
    }

    public static void shareMulFileTextToApp(Activity activity, IntentShare intentShare, String packageName, String activityName){
        Intent it = checkShareToApp(activity.getApplicationContext(), IntentShare.TYPE_ALL, packageName, activityName);
        if (it != null && checkShareSize(IntentShare.TYPE_ALL, intentShare.getVideoUri(), packageName)) {
            it.putExtra(Intent.EXTRA_TEXT, intentShare.getText());
            it.putParcelableArrayListExtra(Intent.EXTRA_STREAM, intentShare.getFileUriList());
            activity.startActivityForResult(Intent.createChooser(it, "Sharing.."), REQUEST_CODE);
        } else {
            ToastUtil.showToast("分享视频失败");
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
        Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setType(type);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if(resInfo != null && !resInfo.isEmpty()){
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            for (ResolveInfo info : resInfo) {
                ActivityInfo activityInfo = info.activityInfo;
                boolean isMatch = activityInfo.packageName.toLowerCase(Locale.ENGLISH).equals(packageName);
                isMatch = isMatch && (activityName == null || activityInfo.name.contains(activityName));
                isMatch = isMatch && checkAllowShare(type, packageName);
                if (isMatch) {
                    shareIntent.setPackage(activityInfo.packageName);
                    shareIntent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                    return shareIntent;
                }
            }
        }
        return null;
    }

    private static boolean checkAllowShare(String type, String packageName) {
        CheckItem item = new CheckItem(type, packageName);
        return !sDisallowArray.contains(item);
    }

    private static boolean checkShareSize(String type, Uri uri, String packageName) {
        CheckItem item = new CheckItem(type, packageName);
        if(sSizeCheckMap.containsKey(item)){
            long maxSize = sSizeCheckMap.get(item);
            File file = new File(uri.toString());
            long uriSize = file.length();
            return uriSize < maxSize;
        }
        return true;
    }

    //
    public static Map<CheckItem, Long> sSizeCheckMap = new HashMap<CheckItem, Long>(){{

    }};

    //过滤分享不出去和分享崩溃的选项
    public static List<CheckItem> sDisallowArray = new ArrayList<CheckItem>(){{

    }};

    static class CheckItem{
        String mType;
        String mPackageName;

        public CheckItem(String type, String packageName) {
            mType = type;
            mPackageName = packageName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CheckItem that = (CheckItem) o;
            return Objects.equals(mType, that.mType) &&
                    Objects.equals(mPackageName, that.mPackageName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(mType, mPackageName);
        }
    }
}
