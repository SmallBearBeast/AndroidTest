package com.bear.libcommon.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
// TODO: 2019-07-18 AppUtil整理
// TODO: 2019-07-21 FileProviderUtil整理
// TODO: 2019-07-21 NetWorkUtil整理
// TODO: 2019-07-21 NotchScreenUtil整理
// TODO: 2019-07-21 整理OsUtil
// TODO: 2019-07-21 整理ServiceUtil
public final class AppUtil extends AppInitUtil{
    /**
     * 安装app
     */
    public static void installApp(final String FILE_PATH){
        installApp(new File(FILE_PATH));
    }

    public static void installApp(File file){
        if(isFileExist(file)){
            getContext().startActivity(getInstallAppIntent(file, true));
        }
    }

    public static void installApp(final Activity ACTIVITY, final String FILE_PATH, final int REQUEST_CODE) {
        installApp(ACTIVITY, new File(FILE_PATH), REQUEST_CODE);
    }

    public static void installApp(final Activity ACTIVITY, final File FILE, final int REQUEST_CODE) {
        if (isFileExist(FILE)){
            ACTIVITY.startActivityForResult(getInstallAppIntent(FILE, false), REQUEST_CODE);
        }
    }

    private static Intent getInstallAppIntent(final File FILE, final boolean IS_NEW_TASK) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(FILE);
        } else {
            String authority = getContext().getPackageName() + ".utilcode.provider"; //换成自己应用的provider
            data = FileProvider.getUriForFile(getContext(), authority, FILE);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        getContext().grantUriPermission(getContext().getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(data, type);
        return IS_NEW_TASK ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }
    /**安装app**/

    /**
     * 卸载app
     */
    public static void uninstallApp(final String PACKAGE_NAME) {
        if(isSpace(PACKAGE_NAME))
            return;
        getContext().startActivity(getUninstallAppIntent(PACKAGE_NAME, true));
    }

    public static void uninstallApp(final Activity ACTIVITY, final String PACKAGE_NAME, final int REQUEST_CODE) {
        if (isSpace(PACKAGE_NAME)) return;
        ACTIVITY.startActivityForResult(getUninstallAppIntent(PACKAGE_NAME, false), REQUEST_CODE);
    }

    private static Intent getUninstallAppIntent(final String PACKAGE_NAME, final boolean IS_NEW_TASK) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + PACKAGE_NAME));
        return IS_NEW_TASK ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }
    /**卸载app**/

    /**
     * 判断其他app是否安装
     */
    public static boolean isAppInstalled(@NonNull final String packageName) {
        PackageManager packageManager = getContext().getPackageManager();
        try {
            return packageManager.getApplicationInfo(packageName, 0) != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * app是否是debug状态
     */
    public static boolean isAppDebug() {
        return isAppDebug(getContext().getPackageName());
    }

    public static boolean isAppDebug(final String PACKAGE_NAME){
        if (isSpace(PACKAGE_NAME))
            return false;
        try {
            PackageManager pm = getContext().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(PACKAGE_NAME, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**app是否是debug状态**/

    /**
     * app是否是系统app
     */
    public static boolean isAppSystem(){
        return isAppSystem(getContext().getPackageName());
    }

    public static boolean isAppSystem(final String PACKAGE_NAME) {
        if (isSpace(PACKAGE_NAME))
            return false;
        try {
            PackageManager pm = getContext().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(PACKAGE_NAME, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**app是否是系统app**/


    /**
     * 启动app
     */
    public static void launchApp(final String PACKAGE_NAME) {
        if (isSpace(PACKAGE_NAME))
            return;
        getContext().startActivity(getLaunchAppIntent(PACKAGE_NAME, true));
    }

    public static void launchApp(final Activity ACTIVITY, final String PACKAGE_NAME, final int REQUEST_CODE) {
        if (isSpace(PACKAGE_NAME))
            return;
        ACTIVITY.startActivityForResult(getLaunchAppIntent(PACKAGE_NAME), REQUEST_CODE);
    }

    private static Intent getLaunchAppIntent(final String PACKAGE_NAME) {
        return getLaunchAppIntent(PACKAGE_NAME, false);
    }

    private static Intent getLaunchAppIntent(final String PACKAGE_NAME, final boolean IS_NEW_TASK) {
        Intent intent = getContext().getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
        if (intent == null)
            return null;
        return IS_NEW_TASK ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }
    /**启动app**/

    /**
     * 重新启动app
     */
    public static void relaunchApp() {
        relaunchApp(false);
    }

    public static void relaunchApp(final boolean IS_KILL_PROCESS) {
        PackageManager packageManager = getContext().getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(getContext().getPackageName());
        if (intent == null)
            return;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getContext().startActivity(intent);
        if (!IS_KILL_PROCESS)
            return;
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    /**重新启动app**/
    
    
    /**
     * 判断app是否在前台
     */
    public static boolean isAppForeground() {
        ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null)
            return false;
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0)
            return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(getContext().getPackageName());
            }
        }
        return false;
    }

    public static boolean isAppForeground(final String PACKAGE_NAME) {
        return !isSpace(PACKAGE_NAME) && PACKAGE_NAME.equals(getForegroundProcessName());
    }
    /**判断app是否在前台**/

    /**
     * 获取前台进程名字
     */
    private static String getForegroundProcessName() {
        ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> pInfo = am.getRunningAppProcesses();
        if (pInfo != null && pInfo.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo aInfo : pInfo) {
                if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return aInfo.processName;
                }
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            PackageManager pm = getContext().getPackageManager();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() <= 0) {
                Log.i("AppUtil", "getForegroundProcessName: noun of access to usage information.");
                return "";
            }
            try {
                ApplicationInfo info = pm.getApplicationInfo(getContext().getPackageName(), 0);
                AppOpsManager aom = (AppOpsManager) getContext().getSystemService(Context.APP_OPS_SERVICE);
                if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName) != AppOpsManager.MODE_ALLOWED) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
                if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName) != AppOpsManager.MODE_ALLOWED) {
                    Log.i("AppUtil", "getForegroundProcessName: refuse to device usage stats.");
                    return "";
                }
                UsageStatsManager usageStatsManager = (UsageStatsManager) getContext().getSystemService(Context.USAGE_STATS_SERVICE);
                List<UsageStats> usageStatsList = null;
                if (usageStatsManager != null) {
                    long endTime = System.currentTimeMillis();
                    long beginTime = endTime - 86400000 * 7;
                    usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, beginTime, endTime);
                }
                if (usageStatsList == null || usageStatsList.isEmpty())
                    return null;
                UsageStats recentStats = null;
                for (UsageStats usageStats : usageStatsList) {
                    if (recentStats == null || usageStats.getLastTimeUsed() > recentStats.getLastTimeUsed()) {
                        recentStats = usageStats;
                    }
                }
                return recentStats == null ? null : recentStats.getPackageName();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    /**获取前台进程名字**/

    /**
     * 获取应用图标
     */
    public static Drawable getAppIcon() {
        return getAppIcon(getContext().getPackageName());
    }
    
    public static Drawable getAppIcon(final String PACKAGE_NAME) {
        if (isSpace(PACKAGE_NAME))
            return null;
        try {
            PackageManager pm = getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(PACKAGE_NAME, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**获取应用图标**/

    /**
     * 获取应用包名
     */
    public static String getAppPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 获取应用名
     */
    public static String getAppName() {
        return getAppName(getContext().getPackageName());
    }

    public static String getAppName(final String PACKAGE_NAME) {
        if (isSpace(PACKAGE_NAME)) return "";
        try {
            PackageManager pm = getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(PACKAGE_NAME, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**获取应用名**/

    /**
     * 获取应用路径
     */
    public static String getAppPath() {
        return getAppPath(getContext().getPackageName());
    }
    
    public static String getAppPath(final String PACKAGE_NAME) {
        if (isSpace(PACKAGE_NAME))
            return "";
        try {
            PackageManager pm = getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(PACKAGE_NAME, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**获取应用路径**/

    /**
     * 获取应用版本名
     */
    public static String getAppVersionName() {
        return getAppVersionName(getContext().getPackageName());
    }
    
    public static String getAppVersionName(final String PACKAGE_NAME) {
        if (isSpace(PACKAGE_NAME))
            return "";
        try {
            PackageManager pm = getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(PACKAGE_NAME, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**获取应用版本名**/

    public static PackageManager getPackageManager(){
        return getContext().getPackageManager();
    }

    /**
     * 是否首次安装
     */
    public static boolean isFirstInstall() {
        try {
            PackageManager mg = getPackageManager();
            PackageInfo info = mg.getPackageInfo(getContext().getPackageName(), 0);
            return info.firstInstallTime == info.lastUpdateTime;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取应用版本号
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(getContext().getPackageName());
    }
    
    public static int getAppVersionCode(final String PACKAGE_NAME) {
        if (isSpace(PACKAGE_NAME))
            return -1;
        try {
            PackageManager pm = getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(PACKAGE_NAME, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }
    /**获取应用版本号**/

    /**
     * 获取AppInfo信息
     */
    public static AppInfo getAppInfo() {
        return getAppInfo(getContext().getPackageName());
    }

    public static AppInfo getAppInfo(final String PACKAGE_NAME) {
        try {
            PackageManager pm = getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(PACKAGE_NAME, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = getContext().getPackageManager();
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null)
                continue;
            list.add(ai);
        }
        return list;
    }

    public static AppInfo getApkInfo(final File APK_FILE) {
        if (APK_FILE == null || !APK_FILE.isFile() || !APK_FILE.exists()) return null;
        return getApkInfo(APK_FILE.getAbsolutePath());
    }

    public static AppInfo getApkInfo(final String APK_FILE_PATH) {
        if (isSpace(APK_FILE_PATH))
            return null;
        PackageManager pm = getContext().getPackageManager();
        PackageInfo pi = pm.getPackageArchiveInfo(APK_FILE_PATH, 0);
        ApplicationInfo appInfo = pi.applicationInfo;
        appInfo.sourceDir = APK_FILE_PATH;
        appInfo.publicSourceDir = APK_FILE_PATH;
        return getBean(pm, pi);
    }

    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pm == null || pi == null)
            return null;
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }
    /**获取AppInfo信息**/

    public static class AppInfo {
        private String packageName;
        private String name;
        private Drawable icon;
        private String packagePath;
        private String versionName;
        private int    versionCode;
        private boolean isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(final int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        public AppInfo(String packageName, String name, Drawable icon, String packagePath,
                       String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        @Override
        public String toString() {
            return "{" +
                    "\n  pkg name: " + getPackageName() +
                    "\n  app icon: " + getIcon() +
                    "\n  app name: " + getName() +
                    "\n  app path: " + getPackagePath() +
                    "\n  app v name: " + getVersionName() +
                    "\n  app v code: " + getVersionCode() +
                    "\n  is system: " + isSystem() +
                    "}";
        }
    }

    private static boolean isSpace(final String S) {
        if (S == null)
            return true;
        for (int i = 0, len = S.length(); i < len; i++) {
            if (!Character.isWhitespace(S.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isFileExist(File file) {
        return file != null && file.exists();
    }
}
