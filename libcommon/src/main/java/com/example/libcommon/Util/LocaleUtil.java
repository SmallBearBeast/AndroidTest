package com.example.libcommon.Util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LocaleUtil extends AppInitUtil{
    private static Locale sAppLocale;
    private static Locale sSystemLocale;

    public static void initAppLocale(Locale locale){
        sAppLocale = locale;
    }

    public static void initSysLocale(Locale locale){
        sSystemLocale = locale;
    }

    /**
     * Application.attachBaseContext
     * Acitivity.attachBaseContext
     * Service.attachBaseContext
     */
    private static Context updateLocaleContext() {
        if(sAppLocale == null){
            return getContext();
        }
        Locale.setDefault(sAppLocale);
        Context context = getContext();
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(sAppLocale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = sAppLocale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    public static Locale getSystemLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * switch language should update applicationContext config
     */
    public static void updateAppLocale(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = sAppLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(sAppLocale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(sAppLocale);
        }
        resources.updateConfiguration(config, dm);
    }
}
