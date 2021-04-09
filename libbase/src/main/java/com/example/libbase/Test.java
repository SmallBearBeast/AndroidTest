package com.example.libbase;

import com.example.libbase.Storage.FileStorage;
import com.example.libbase.Storage.MmpStorage;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        long startTs = System.currentTimeMillis();
        int count = 10;
        for (int i = 0; i < count; i++) {
            testFileStorage();
        }
        System.out.println(count + " times testFileStorage cost " + (System.currentTimeMillis() - startTs) + "ms");

        startTs = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            testMmpStorage();
        }
        System.out.println(count + " times testFileStorage cost " + (System.currentTimeMillis() - startTs) + "ms");
    }

    private static void testFileStorage() {
        String TEST_PATH_1 = "/Users/hugo.wu/Desktop/FileStorage";
        List<TestObj> tempObjList = new ArrayList<>();
        tempObjList.add(new TestObj("0000"));
        List<TestObj> testObjList = new ArrayList<>();
        testObjList.add(new TestObj("1111", tempObjList));
        testObjList.add(new TestObj("2222", tempObjList));
        testObjList.add(new TestObj("3333", tempObjList));
        FileStorage.writeObjToJson(TEST_PATH_1, testObjList);

        testObjList = FileStorage.readObjFromJson(TEST_PATH_1, new TypeToken<List<TestObj>>(){});
//        System.out.println("FileStorage testObjList = " + testObjList);
    }

    private static void testMmpStorage() {
        String TEST_PATH_2 = "/Users/hugo.wu/Desktop/MmpStorage";
        List<TestObj> tempObjList = new ArrayList<>();
        tempObjList.add(new TestObj("0000"));
        List<TestObj> testObjList = new ArrayList<>();
        testObjList.add(new TestObj("4444", tempObjList));
        testObjList.add(new TestObj("5555", tempObjList));
        testObjList.add(new TestObj("6666", tempObjList));
        MmpStorage.writeObjToJson(TEST_PATH_2, testObjList);

        testObjList = MmpStorage.readObjFromJson(TEST_PATH_2, new TypeToken<List<TestObj>>(){});
//        System.out.println("MmpStorage testObjList = " + testObjList);
    }
}
