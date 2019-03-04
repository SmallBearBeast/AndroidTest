package com.example.administrator.androidtest.Common.Util.Core;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class SettingsUtil {
    public static void launchAppDetailsSettings() {
        launchAppDetailsSettings(AppUtil.getApp().getPackageName());
    }

    public static void launchAppDetailsSettings(final String PACKAGE_NAME) {
        if (StringUtil.isSpace(PACKAGE_NAME))
            return;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + PACKAGE_NAME));
        AppUtil.getApp().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
