package com.example.libbase.Util;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class SettingsUtil {
    public static void openDetailsSettings() {
        openDetailsSettings(AppUtil.getApp().getPackageName());
    }

    public static void openDetailsSettings(final String PACKAGE_NAME) {
        if (StringUtil.isSpace(PACKAGE_NAME))
            return;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + PACKAGE_NAME)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppUtil.getApp().startActivity(intent);
    }

    public static void openWirelessSettings() {
        AppUtil.getApp().startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
