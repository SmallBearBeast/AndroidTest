package com.example.administrator.androidtest.SerImpl;

import java.nio.ByteBuffer;

public class School implements SerInterface {
    private String name;
    @Override
    public int size() {
        return SerHelper.calSerSize(name);
    }

    @Override
    public ByteBuffer serOut() {
        ByteBuffer buf = ByteBuffer.allocate(size());
        SerHelper.serOut(buf, name, String.class);
        return buf;
    }

    @Override
    public void serIn(ByteBuffer in) {

    }
}
