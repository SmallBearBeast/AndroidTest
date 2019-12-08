package com.example.libmmf.SerImpl;

import java.nio.ByteBuffer;

// TODO: 2019-08-21 考虑是否接入protobuf
public interface SerInterface {
    int size();

    ByteBuffer outBuffer();

    byte[] outBytes();

    void in(ByteBuffer in);
}
