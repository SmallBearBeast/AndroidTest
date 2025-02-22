package com.example.administrator.androidtest.demo;

import com.bear.libkv.MmkvVal.MmkvBoolVal;
import com.bear.libkv.MmkvVal.MmkvFloatVal;
import com.bear.libkv.MmkvVal.MmkvIntVal;
import com.bear.libkv.MmkvVal.MmkvStringVal;


public class MmkvValHelper {
    public static final String MMKV_GLOBAL_CONFIG = "mmkv_global_config";
    private static final String MMKV_USER_CONFIG = "mmkv_user_config_";
    private static long UID;

    // sp_global_config
    public static final MmkvFloatVal testFloatSp = new MmkvFloatVal("testFloatSp", 1.5f);
    public static final MmkvIntVal testIntSp = new MmkvIntVal(MMKV_GLOBAL_CONFIG, "testIntSp", 1);
    public static final MmkvBoolVal testBoolSp = new MmkvBoolVal(MMKV_GLOBAL_CONFIG, "testBoolSp", false);
    public static final MmkvStringVal testStringSp = new MmkvStringVal(MMKV_GLOBAL_CONFIG, "testStringSp", "hello");

    // sp_user_config
    public static MmkvIntVal userTestIntSp;
    public static MmkvStringVal userTestStringSp;

    private static String getUserConfigKey() {
        return MMKV_USER_CONFIG + UID;
    }

    public static void changeUserId(long uid) {
        if (UID != uid) {
            UID = uid;
            userTestIntSp = new MmkvIntVal(getUserConfigKey(), "man_enter_room_count", 0);
            userTestStringSp = new MmkvStringVal(getUserConfigKey(), "man_enter_room_count_one_day", "");
        }
    }
}
