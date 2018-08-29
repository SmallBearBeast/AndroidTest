package com.example.administrator.androidtest.SerImpl;

import java.nio.ByteBuffer;

public interface SerInterface {
    int size();

    ByteBuffer serOut();

    void serIn(ByteBuffer in);
}
