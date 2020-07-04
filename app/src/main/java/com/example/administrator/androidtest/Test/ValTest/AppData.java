package com.example.administrator.androidtest.Test.ValTest;

import android.app.Application;

import com.example.libmmf.AppVal.AppBoolVal;
import com.example.libmmf.AppVal.AppFloatVal;
import com.example.libmmf.AppVal.AppIntVal;
import com.example.libmmf.AppVal.AppStringVal;
import com.example.libmmf.AppVal.AppVal;

public class AppData {
    public static final String TEST_1 = "test_1";
    public static final String TEST_2 = "test_2";

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
