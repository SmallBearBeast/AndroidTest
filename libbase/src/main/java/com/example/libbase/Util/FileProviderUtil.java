package com.example.libbase.Util;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;

public class FileProviderUtil extends AppInitUtil {
    /**
     * 从file获取Uri(兼容N)
     */
    public static Uri getUriForFile(File file) {
        if(isFileExist(file)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return getUriForFile24(file);
            } else {
                return Uri.fromFile(file);
            }
        }
        return null;
    }

    public static Uri getUriForFile(String path){
        if(isFileExist(path)){
            return getUriForFile(new File(path));
        }
        return null;
    }

    private static Uri getUriForFile24(File file) {
        return FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".fileprovider", file);
    }
    /**从file获取Uri(兼容N)**/

    public static void setIntentDataAndType(Intent intent, String type, File file, boolean writeAble) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setDataAndType(getUriForFile(file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }

    public static void setIntentData(Intent intent, File file, boolean writeAble) {
        setIntentDataAndType(intent, null, file, writeAble);
    }

    public static void grantPermissions(Intent intent, Uri uri, boolean writeAble) {
        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
        if (writeAble) {
            flag |= Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        }
        intent.addFlags(flag);
        List<ResolveInfo> resInfoList = getContext().getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            getContext().grantUriPermission(packageName, uri, flag);
        }
    }

    private static boolean isFileExist(String path) {
        return isFileExist(new File(path));
    }

    private static boolean isFileExist(File file) {
        return file != null && file.exists();
    }
}
