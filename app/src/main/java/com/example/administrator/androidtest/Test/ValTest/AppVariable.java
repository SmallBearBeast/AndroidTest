package com.example.administrator.androidtest.Test.ValTest;

import android.app.Application;

import com.bear.libkv.AppVal.AppBoolVal;
import com.bear.libkv.AppVal.AppFloatVal;
import com.bear.libkv.AppVal.AppIntVal;
import com.bear.libkv.AppVal.AppLongVal;
import com.bear.libkv.AppVal.AppStringVal;


public class AppVariable {
    private static final String SP_GLOBAL_CONFIG = "sp_global_config";
    private static final String SP_USER_CONFIG = "sp_user_config_";
    private static long UID;

    // sp_global_config


    // sp_user_config
    public static AppIntVal manEnterRoomCount;
    public static AppLongVal manEnterRoomCountOneDay;


    static {
        changeUserId(0L);
    }

    private static String getUserConfigKey() {
        return SP_USER_CONFIG + UID;
    }

    public static void changeUserId(long uid) {
        if (UID != uid) {
            UID = uid;
            manEnterRoomCount = new AppIntVal(getUserConfigKey(), "man_enter_room_count", 0);
            manEnterRoomCountOneDay = new AppLongVal(getUserConfigKey(), "man_enter_room_count_one_day", 0L);
        }
    }
    public static final String TEST_1 = "test_1";
    public static final String TEST_2 = "test_2";

    // 会出现初始化错误，因为内部的静态变量初始化更加提前。
//    public static void init(Application app) {
//        AppVal.init(app, TEST_1, TEST_2);
//    }

    public static final AppFloatVal test0_float1 = new AppFloatVal("test0_float1", 1.5f);

    public static final AppIntVal test1_int1 = new AppIntVal(TEST_1, "test1_int1", 1);
    public static final AppIntVal test1_int2 = new AppIntVal(TEST_1, "test1_int2", 2);
    public static final AppIntVal test1_int3 = new AppIntVal(TEST_1, "test1_int3", 3);
    public static final AppIntVal test1_int4 = new AppIntVal(TEST_1, "test1_int4", 4);
    public static final AppIntVal test1_int5 = new AppIntVal(TEST_1, "test1_int5", 5);

    public static final AppBoolVal test2_bool1 = new AppBoolVal(TEST_2, "test2_bool1", false);
    public static final AppStringVal test2_string2 = new AppStringVal(TEST_2, "test2_string2", "hello");
}
