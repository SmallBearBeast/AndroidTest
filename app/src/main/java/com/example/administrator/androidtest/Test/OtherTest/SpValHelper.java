package com.example.administrator.androidtest.Test.OtherTest;

import com.bear.libkv.AppVal.SpBoolVal;
import com.bear.libkv.AppVal.SpFloatVal;
import com.bear.libkv.AppVal.SpIntVal;
import com.bear.libkv.AppVal.SpLongVal;
import com.bear.libkv.AppVal.SpStringVal;


public class SpValHelper {
    public static final String SP_GLOBAL_CONFIG = "sp_global_config";
    private static final String SP_USER_CONFIG = "sp_user_config_";
    private static long UID;

    // sp_global_config
    public static final SpFloatVal testFloatSp = new SpFloatVal("testFloatSp", 1.5f);
    public static final SpIntVal testIntSp = new SpIntVal(SP_GLOBAL_CONFIG, "testIntSp", 1);
    public static final SpBoolVal testBoolSp = new SpBoolVal(SP_GLOBAL_CONFIG, "testBoolSp", false);
    public static final SpStringVal testStringSp = new SpStringVal(SP_GLOBAL_CONFIG, "testStringSp", "hello");

    // sp_user_config
    public static SpIntVal userTestIntSp;
    public static SpStringVal userTestStringSp;

    private static String getUserConfigKey() {
        return SP_USER_CONFIG + UID;
    }

    public static void changeUserId(long uid) {
        if (UID != uid) {
            UID = uid;
            userTestIntSp = new SpIntVal(getUserConfigKey(), "man_enter_room_count", 0);
            userTestStringSp = new SpStringVal(getUserConfigKey(), "man_enter_room_count_one_day", "");
        }
    }
}
