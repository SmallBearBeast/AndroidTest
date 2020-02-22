package com.example.libmmf.AppVal;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.atomic.AtomicInteger;

class AppVal {
    private static final String SP_BASE_NAME = "sp_base_name";
    private static final int SP_LENGTH_LIMIT = 20;
    static final String SP_FILE_LENGTH = "sp_file_length";
    static Application sApp;
    static String sBaseFileName;
    static AtomicInteger sSpFileCount = new AtomicInteger(0);
    static int sSpLengthLimit = SP_LENGTH_LIMIT;

    String mKey;
    int mGroup = -1;

    public static void init(Application app) {
        init(app, SP_BASE_NAME, SP_LENGTH_LIMIT);
    }

    private static void init(Application app, String baseAppValFileName) {
        init(app, baseAppValFileName, SP_LENGTH_LIMIT);
    }

    private static void init(Application app, String baseAppValFileName, int spLengthLimit) {
        sApp = app;
        sBaseFileName = baseAppValFileName;
        sSpLengthLimit = spLengthLimit;
        while (true) {
            SharedPreferences sp = sApp.getSharedPreferences(sBaseFileName + "_" + sSpFileCount, Context.MODE_PRIVATE);
            sSpFileCount.incrementAndGet();
            if (!sp.contains(SP_FILE_LENGTH)) {
                break;
            }
        }
    }
}
