package com.example.libbase.Util;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 */
public class PackageUtil extends AppInitUtil {
    private static volatile String sVersionName = "";
    private static volatile int sVersionCode = 0;
    private static volatile String sChannel = "";

    public static String getVersionName() {
        if (TextUtils.isEmpty(sVersionName)) {
            PackageInfo pi;
            try {
                pi = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
                sVersionName = pi.versionName;
            } catch (Exception e) {}

        }
        return sVersionName;
    }

    public static int getVersionCode() {
        if (sVersionCode == 0) {
            try {
                PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
                sVersionCode = pi.versionCode;
            } catch (Exception e) {}
        }
        return sVersionCode;
    }

    public static PackageManager getPackageManager() {
        return AppInitUtil.sContext.getPackageManager();
    }

    public static String getPackageName() {
        return AppInitUtil.sContext.getPackageName();
    }

    /**
     * 是否首次安装
     */
    public static boolean isFirstInstall() {
        try {
            PackageManager mg = getPackageManager();
            PackageInfo info = mg.getPackageInfo(AppInitUtil.sContext.getPackageName(), 0);
            return info.firstInstallTime == info.lastUpdateTime;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getChannel() {
        if (TextUtils.isEmpty(sChannel)) {
            try {
                ApplicationInfo ai = getPackageManager().getApplicationInfo(
                        getPackageName(), PackageManager.GET_META_DATA);
                sChannel = (String) ai.metaData.get("APP_CHANNEL");
            } catch (Exception e) {
            }
        }
        return sChannel;
    }

    /**
     * 获取应用目录的相关信息
     *
     * @return
     */
    public static String getApplicationWorkspaceInfo() {
        StringBuilder sb = new StringBuilder();
        Context context = AppInitUtil.sContext;
        try {
            sb.append("SOURCE_PATH=");
            sb.append(context.getApplicationInfo().sourceDir);
            sb.append(" :");
            sb.append(new File(context.getApplicationInfo().sourceDir).length());
            sb.append('\n');
            sb.append("FILES_PATH=");
            sb.append(context.getFilesDir().getAbsolutePath());
            sb.append('\n');
            sb.append("LIB_PATH=");
            sb.append(context.getApplicationInfo().nativeLibraryDir);
            sb.append('\n');
            sb.append("LIB_LIST=");
            File libFile = new File(context.getApplicationInfo().nativeLibraryDir);
            getFilesList(sb, libFile, libFile.list());
            sb.append('\n');
            sb.append("LIB_EXT_LIST=");
            File libExtFile = new File(context.getFilesDir().getAbsolutePath().replace("files", "app_lib_ext"));
            getFilesList(sb, libExtFile, libExtFile.list());
            sb.append('\n');
            sb.append("libs.7z=");
            getFilesList(sb, context.getCacheDir(), context.getCacheDir().list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.startsWith("libs.7z");
                }
            }));
            sb.append('\n');
        } catch (Exception e) {
        }

        return sb.toString();
    }

    private static void getFilesList(StringBuilder sb, File folder, String[] files) {
        if (files == null) {
            return;
        }

        for (String file : files) {
            sb.append(file);
            sb.append(' ');
            sb.append(new File(folder, file).length());
            sb.append(' ');
        }
    }

}
