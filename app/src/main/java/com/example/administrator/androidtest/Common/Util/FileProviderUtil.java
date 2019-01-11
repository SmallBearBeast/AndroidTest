package com.example.administrator.androidtest.Common.Util;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;

public class FileProviderUtil extends AppInitUtil{
    public static Uri getUriForFile(File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    private static Uri getUriForFile24(File file) {
        Uri fileUri = FileProvider.getUriForFile(sContext, sContext.getPackageName() + ".fileprovider", file);
        return fileUri;
    }

    public static void setIntentDataAndType(Intent intent,
                                            String type,
                                            File file,
                                            boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }

    public static void setIntentData(Intent intent,
                                     File file,
                                     boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setData(getUriForFile(file));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setData(Uri.fromFile(file));
        }
    }

    public static void grantPermissions(Intent intent, Uri uri, boolean writeAble) {

        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
        if (writeAble) {
            flag |= Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        }
        intent.addFlags(flag);
        List<ResolveInfo> resInfoList = sContext.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            sContext.grantUriPermission(packageName, uri, flag);
        }
    }
}
