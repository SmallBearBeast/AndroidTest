package com.example.administrator.androidtest.Test.ValTest;

import com.bear.libkv.AppVal.SpBoolVal;
import com.bear.libkv.AppVal.SpFloatVal;
import com.bear.libkv.AppVal.SpIntVal;
import com.bear.libkv.AppVal.SpLongVal;
import com.bear.libkv.AppVal.SpStringVal;


public class AppVariable {
    private static final String SP_GLOBAL_CONFIG = "sp_global_config";
    private static final String SP_USER_CONFIG = "sp_user_config_";
    private static long UID;

    // sp_global_config


    // sp_user_config
    public static SpIntVal manEnterRoomCount;
    public static SpLongVal manEnterRoomCountOneDay;


    static {
        changeUserId(0L);
    }

    private static String getUserConfigKey() {
        return SP_USER_CONFIG + UID;
    }

    public static void changeUserId(long uid) {
        if (UID != uid) {
            UID = uid;
            manEnterRoomCount = new SpIntVal(getUserConfigKey(), "man_enter_room_count", 0);
            manEnterRoomCountOneDay = new SpLongVal(getUserConfigKey(), "man_enter_room_count_one_day", 0L);
        }
    }
    public static final String TEST_1 = "test_1";
    public static final String TEST_2 = "test_2";

    // 会出现初始化错误，因为内部的静态变量初始化更加提前。
//    public static void init(Application app) {
//        AppVal.init(app, TEST_1, TEST_2);
//    }

    public static final SpFloatVal test0_float1 = new SpFloatVal("test0_float1", 1.5f);

    public static final SpIntVal test1_int1 = new SpIntVal(TEST_1, "test1_int1", 1);
    public static final SpIntVal test1_int2 = new SpIntVal(TEST_1, "test1_int2", 2);
    public static final SpIntVal test1_int3 = new SpIntVal(TEST_1, "test1_int3", 3);
    public static final SpIntVal test1_int4 = new SpIntVal(TEST_1, "test1_int4", 4);
    public static final SpIntVal test1_int5 = new SpIntVal(TEST_1, "test1_int5", 5);

    public static final SpBoolVal test2_bool1 = new SpBoolVal(TEST_2, "test2_bool1", false);
    public static final SpStringVal test2_string2 = new SpStringVal(TEST_2, "test2_string2", "hello");
}
