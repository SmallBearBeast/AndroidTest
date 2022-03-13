package com.example.administrator.androidtest.Test.MainTest;

import com.bear.libkv.KV;
import com.bear.libkv.SpVal.SpHelper;


public class SpValHelper {
    public static final String SP_GLOBAL_CONFIG = "sp_global_config";
    private static final String SP_USER_CONFIG = "sp_user_config_";
    private static long UID;

    // sp_global_config
    public static final KV<Float> testFloatSp = SpHelper.create("testFloatSp", 1.5f);
    public static final KV<Integer> testIntSp = SpHelper.create(SP_GLOBAL_CONFIG, "testIntSp", 1);
    public static final KV<Boolean> testBoolSp = SpHelper.create(SP_GLOBAL_CONFIG, "testBoolSp", false);
    public static final KV<String> testStringSp = SpHelper.create(SP_GLOBAL_CONFIG, "testStringSp", "hello");

    // sp_user_config
    public static KV<Integer> userTestIntSp;
    public static KV<String> userTestStringSp;

    private static String getUserConfigKey() {
        return SP_USER_CONFIG + UID;
    }

    public static void changeUserId(long uid) {
        if (UID != uid) {
            UID = uid;
            userTestIntSp = SpHelper.create(getUserConfigKey(), "man_enter_room_count", 0);
            userTestStringSp = SpHelper.create(getUserConfigKey(), "man_enter_room_count_one_day", "");
        }
    }
}
