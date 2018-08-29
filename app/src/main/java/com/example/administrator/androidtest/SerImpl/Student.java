package com.example.administrator.androidtest.SerImpl;

import java.nio.ByteBuffer;

public class Student implements SerInterface {
    private String name;
    private int age;
    @Override
    public int size() {
        return 4 + SerHelper.calSerSize(name);
    }

    @Override
    public ByteBuffer serOut() {
        Short[] shorts = new Short[10];
        SerHelper.calSerSize(shorts);
        return null;
    }

    @Override
    public void serIn(ByteBuffer in) {

    }
}
