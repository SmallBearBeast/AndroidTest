package com.bear.libcommon.util;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class SettingsUtil extends AppInitUtil{
    public static void openDetailsSettings() {
        openDetailsSettings(getContext().getPackageName());
    }

    public static void openDetailsSettings(final String PACKAGE_NAME) {
        if (isSpace(PACKAGE_NAME))
            return;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + PACKAGE_NAME)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    public static void openWirelessSettings() {
        getContext().startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
}
