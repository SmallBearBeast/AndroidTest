package com.example.libmmf.Mmf;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

// TODO: 2020-02-28 Use MMKV instead
public class MmapKV {
    private static final int MAP_SIZE = 4096;
    private static class SingleTon{
        private static MmapKV sMmapKV = new MmapKV();
    }

    public static MmapKV get() {
        return SingleTon.sMmapKV;
    }

//    private Map<String, >

    private static class MmapData {
        Map<String, Object> map;
        String path;
        MappedByteBuffer mbb;
        RandomAccessFile raf;
        FileChannel fc;

        public static void put(String key, Object value) {

        }

        public static <T> T get(String key, T defaultValue) {
            return defaultValue;
        }
    }
}
