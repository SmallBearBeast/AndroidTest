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
        ByteBuffer buf = ByteBuffer.allocate(size());
        SerHelper.serOut(buf, name, String.class);
        buf.putInt(age);
        return buf;
    }

    @Override
    public void serIn(ByteBuffer in) {

    }

}
