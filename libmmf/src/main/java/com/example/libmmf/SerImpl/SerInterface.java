package com.example.libmmf.SerImpl;

import java.nio.ByteBuffer;

public interface SerInterface {
    int size();

    ByteBuffer outBuffer();

    byte[] outBytes();

    void in(ByteBuffer in);
}
