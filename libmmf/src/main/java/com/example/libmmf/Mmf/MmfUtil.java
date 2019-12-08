package com.example.libmmf.Mmf;


import com.example.libbase.Util.FileUtil;
import com.example.libmmf.SerImpl.SerInterface;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MmfUtil {

    public static int MODE_READ = 0;
    public static int MODE_WRITE = 1;

    /*
        BASE_SIZE最大是2G，超过2G内存映射抛异常
     */
    private static int BASE_SIZE = 128;
    private static Map<String, MappingFile> FILE_MAPPING_MAP = new ConcurrentHashMap<>();

    public static void mapFile(String dir, String fileName, int mode, String tag) {
        String path = dir + File.separator + fileName;
        mapFile(path, mode, tag);
    }

    /*
        内存映射文件操作
     */
    public static void mapFile(String path, int mode, String tag) {
        try {
            if (FileUtil.createFile(path)) {
                RandomAccessFile raf = new RandomAccessFile(path, "rw");
                FileChannel fc = raf.getChannel();
                MappedByteBuffer mbb = null;
                if (mode == MODE_READ) {
                    mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size());
                } else if (mode == MODE_WRITE) {
                    mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, BASE_SIZE);
                }
                FILE_MAPPING_MAP.put(tag, new MappingFile(path, mbb, raf, fc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MappedByteBuffer getMbb(String tag) {
        if (!FILE_MAPPING_MAP.containsKey(tag))
            return null;
        MappingFile mf = FILE_MAPPING_MAP.get(tag);
        return mf.mbb;
    }

    /**
     * 往mmf写入基本数据,t:数据,index:写入位置,写入数组类型的使用{@link #write(Object, String, int, Class)}
     */
    public static int write(Object t, String tag, int index){
        return write(t, tag, index, null);
    }

    /**
     * 往mmf写入数据 t : 数据，index : 写入位置，clz : 数据元素类型（用于复杂数据类型）
     */
    public static int write(Object t, String tag, int index, Class clz) {
        try {
            MappingFile mf = FILE_MAPPING_MAP.get(tag);
            if (t instanceof Integer) {
                MappedByteBuffer mbb = checkSize(mf, 4 + index);
                mbb.putInt(index, (Integer) t);
                return index + 4;
            } else if (t instanceof Byte) {
                MappedByteBuffer mbb = checkSize(mf, 1 + index);
                mbb.put(index, (Byte) t);
                return index + 1;
            } else if (t instanceof Float) {
                MappedByteBuffer mbb = checkSize(mf, 4 + index);
                mbb.putFloat(index, (Float) t);
                return index + 4;
            } else if (t instanceof Long) {
                MappedByteBuffer mbb = checkSize(mf, 8 + index);
                mbb.putLong(index, (Long) t);
                return index + 8;
            } else if (t instanceof Double) {
                MappedByteBuffer mbb = checkSize(mf, 8 + index);
                mbb.putDouble(index, (Double) t);
                return index + 8;
            } else if (t instanceof Short) {
                MappedByteBuffer mbb = checkSize(mf, 2 + index);
                mbb.putShort(index, (Short) t);
                return index + 2;
            } else if (t instanceof Character) {
                MappedByteBuffer mbb = checkSize(mf, 2 + index);
                mbb.putChar(index, (Character) t);
                return index + 2;
            } else if (t instanceof String) {
                Byte[] Bytes = WrapUtil.priToObjArray(1, Byte.class, ((String) t).getBytes());
                index = write(Bytes.length, tag, index, clz);
                for (int i = 0; i < Bytes.length; i++) {
                    index = write(Bytes[i], tag, index, clz);
                }
            } else if (t instanceof Object[]) {
                Object[] objs = (Object[]) t;
                index = write(objs.length, tag, index, clz);
                for (int i = 0; i < objs.length; i++) {
                    index = write(objs[i], tag, index, clz);
                }
            } else if (t instanceof List) {
                List list = (List) t;
                index = write(list.size(), tag, index, clz);
                for (Object obj : list) {
                    index = write(obj, tag, index, clz);
                }
            } else if (t instanceof Map) {
                Map map = (Map) t;
                index = write(map.size(), tag, index, clz);
                Set<Map.Entry> set = map.entrySet();
                for (Map.Entry e : set) {
                    index = write(e.getKey(), tag, index, clz);
                    index = write(e.getValue(), tag, index, clz);
                }
            } else if (t instanceof SerInterface) {
                index = write(((SerInterface) t).size(), tag, index, clz);
                byte[] bytes = ((SerInterface) t).outBytes();
                for (int i = 0; i < bytes.length; i++) {
                    index = write(bytes[i], tag, index, clz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (t == null) {
            if (clz == Byte.class) {
                index = write((byte) 0, tag, index, clz);
            } else if (clz == Short.class) {
                index = write((short) 0, tag, index, clz);
            } else if (clz == Character.class) {
                index = write((char) 0, tag, index, clz);
            } else if (clz == Integer.class) {
                index = write(0, tag, index, clz);
            } else if (clz == Float.class) {
                index = write(0F, tag, index, clz);
            } else if (clz == Double.class) {
                index = write(0D, tag, index, clz);
            } else if (clz == Long.class) {
                index = write(0L, tag, index, clz);
            } else {
                index = write(0, tag, index, clz);
            }
        }
        return index;
    }

    public static void closeMmf(String tag) {
        if (!FILE_MAPPING_MAP.containsKey(tag))
            return;
        MappingFile mf = FILE_MAPPING_MAP.get(tag);
        mf.close();
    }

    private static MappedByteBuffer checkSize(MappingFile mf, int length) throws Exception {
        MappedByteBuffer mbb = mf.mbb;
        int expand = 0;
        int rate = 0;
        if (mbb.capacity() < length) {
            while (expand < length) {
                expand = expand + BASE_SIZE;
                rate++;
            }
        }

        if (rate > 0) {
            mbb = mf.fc.map(FileChannel.MapMode.READ_WRITE, 0, BASE_SIZE * rate);
            mf.mbb = mbb;
        }
        return mbb;
    }

    /**
     * 读取基本数据类型，含有数组类型的需要使用{@link #read(ReadData, String, int, Class)}
     */
    public static int read(ReadData t, String tag, int index){
        return read(t, tag, index, null);
    }

    /**
     * 在index位置读取数据存在ReadData中
     * t : 读取数据的存放载体
     * index : 位置
     * clz : 这个只针对数组类型起作用，指代数组元素类型
     */
    public static int read(ReadData t, String tag, int index, Class clz) {
        MappedByteBuffer mbb = getMbb(tag);
        try {
            if (t.type instanceof Integer) {
                t.data = mbb.getInt(index);
                return index + 4;
            } else if (t.type instanceof Byte) {
                t.data = mbb.get(index);
                return index + 1;
            } else if (t.type instanceof Float) {
                t.data = mbb.getFloat(index);
                return index + 4;
            } else if (t.type instanceof Long) {
                t.data = mbb.getLong(index);
                return index + 8;
            } else if (t.type instanceof Double) {
                t.data = mbb.getDouble(index);
                return index + 8;
            } else if (t.type instanceof Short) {
                t.data = mbb.getShort(index);
                return index + 2;
            } else if (t.type instanceof Character) {
                t.data = mbb.getChar(index);
                return index + 2;
            } else if (t.type instanceof String) {
                ReadData rd = new ReadData(0);
                index = read(rd, tag, index, clz);
                int len = (int) rd.data;
                if (len > 0) {
                    byte[] bytes = new byte[len];
                    rd = new ReadData((byte) 0);
                    for (int i = 0; i < len; i++) {
                        index = read(rd, tag, index, clz);
                        bytes[i] = (byte) rd.data;
                    }
                    t.data = new String(bytes);
                } else {
                    t.data = null;
                }
            } else if (t.type instanceof Object[]) {
                Object[] types = (Object[]) t.type;
                ReadData rd = new ReadData(0);
                index = read(rd, tag, index, clz);
                int len = (int) rd.data;
                if (len > 0) {
                    int[] dim = new int[WrapUtil.arrayLevel(t.type)];
                    dim[0] = len;
                    Object[] result = (Object[]) Array.newInstance(clz, dim);
                    for (int i = 0; i < len; i++) {
                        rd = new ReadData(types[0]);
                        index = read(rd, tag, index, clz);
                        result[i] = rd.data;
                    }
                    t.data = result;
                } else {
                    t.data = null;
                }
            } else if (t.type instanceof List) {
                List list = (List) t.type;
                ReadData rd = new ReadData(0);
                index = read(rd, tag, index, clz);
                int len = (int) rd.data;
                if (len > 0) {
                    Object[] result = new Object[len];
                    for (int i = 0; i < len; i++) {
                        rd = new ReadData(list.get(0));
                        index = read(rd, tag, index, clz);
                        result[i] = rd.data;
                    }
                    t.data = Arrays.asList(result);
                } else {
                    t.data = null;
                }
            } else if (t.type instanceof Map) {
                Map map = (Map) t.type;
                ReadData rd = new ReadData(0);
                index = read(rd, tag, index, clz);
                int len = (int) rd.data;
                if(len > 0){
                    Map result = new HashMap();
                    Set<Map.Entry> set = map.entrySet();
                    for (Map.Entry e : set) {
                        ReadData rdKey = new ReadData(e.getKey());
                        ReadData rdVal = new ReadData(e.getValue());
                        for (int i = 0; i < len; i++) {
                            index = read(rdKey, tag, index, clz);
                            index = read(rdVal, tag, index, clz);
                            result.put(rdKey.data, rdVal.data);
                        }
                        break;
                    }
                    t.data = result;
                }else {
                    t.data = null;
                }
            } else if (t.type instanceof SerInterface) {
                SerInterface ser = (SerInterface) t.type.getClass().getConstructor().newInstance();
                ReadData rd = new ReadData(0);
                index = read(rd, tag, index, clz);
                int len = (int) rd.data;
                if (len > 0) {
                    mbb.position(index);
                    ser.in(mbb);
                    t.data = ser;
                    index = index + len;
                } else {
                    t.data = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public static void initSize(int size) {
        BASE_SIZE = size;
    }

    static class MappingFile {
        String path;
        MappedByteBuffer mbb;
        RandomAccessFile raf;
        FileChannel fc;

        public MappingFile(String path, MappedByteBuffer mbb, RandomAccessFile raf, FileChannel fc) {
            this.path = path;
            this.mbb = mbb;
            this.raf = raf;
            this.fc = fc;
        }

        public void close() {
            try {
                raf.close();
                fc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
        读数据存放载体，必须传入一个类型单元才能进行读数据。
        比如：要读取Map<String, String>类型数据，传入的type也必须是Map<String, String>
     */
    static class ReadData<T> {
        Object data;
        T type;

        public ReadData(T type) {
            this.type = type;
        }

        public <T> T data() {
            return (T) data;
        }
    }
}
