package com.example.administrator.androidtest.SerImpl;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;

public class SerHelper {

    /*
        计算字符串序列化字节大小 + 4（长度）
     */
    public static int calSerSize(String str) {
        if (str == null)
            return 4;
        return 4 + str.getBytes().length;
    }

    /*
         计算数组序列化字节大小 + 4（长度）
     */
    public static <T> int calSerSize(T[] array) {
        int size = 4;
        if (array == null)
            return size;
        for (int i = 0; i < array.length; i++) {
            size = size + serSize(array[i]);
        }
        return size;
    }

    /*
         计算集合序列化字节大小 + 4（长度）
     */
    public static <T> int calSerSize(Collection<T> collection) {
        int size = 4;
        if (collection == null)
            return size;
        for (T t : collection) {
            size = size + serSize(t);
        }
        return size;
    }

    /*
         计算Map序列化字节大小 + 4（长度）
     */
    public static <K, V> int calSerSize(Map<K, V> map) {
        int size = 4;
        if (map == null)
            return size;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            size = size + serSize(entry.getKey());
            size = size + serSize(entry.getValue());
        }
        return size;
    }

    private static <T> int serSize(T t) {
        int size = 0;
        if (t instanceof Integer) {
            size = size + 4;
        } else if (t instanceof Short) {
            size = size + 2;
        } else if (t instanceof Float) {
            size = size + 4;
        } else if (t instanceof Byte) {
            size = size + 1;
        } else if (t instanceof Long) {
            size = size + 8;
        } else if (t instanceof Double) {
            size = size + 8;
        } else if (t instanceof Character) {
            size = size + 1;
        } else if (t instanceof String) {
            size = size + calSerSize((String) t);
        } else if (t instanceof SerInterface) {
            size = size + ((SerInterface) t).size();
        } else if (t instanceof Object[]) {
            size = size + calSerSize((Object[]) t);
        }
        return size;
    }

    /*
        将数组类型数据写到buf
     */
    public static <T> void serOut(ByteBuffer buf, T[] array, Class clz){
        if(array == null)
            buf.putInt(0);
        else {
            buf.putInt(array.length);
            for (int i = 0; i < array.length; i++) {
                serOut(buf, array[i], clz);
            }
        }
    }

    /*
        将集合类型数据写到buf
     */
    public static <T> void serOut(ByteBuffer buf, Collection<T> collection, Class clz){
        if(collection == null)
            buf.putInt(0);
        else {
            buf.putInt(collection.size());
            for (T t : collection) {
                serOut(buf, t, clz);
            }
        }
    }

    /*
        将Map类型数据写到buf
     */
    public static <K, V> void serOut(ByteBuffer buf, Map<K, V> map, Class clzK, Class clzV){
        if(map == null)
            buf.putInt(0);
        else {
            buf.putInt(map.size());
            for (Map.Entry<K, V> entry : map.entrySet()) {
                serOut(buf, entry.getKey(), clzK);
                serOut(buf, entry.getValue(), clzV);
            }
        }
    }

    /*
        将基本类型数据写到buf
     */
    public static <T> void serOut(ByteBuffer buf, T t, Class<T> clz) {
        if (clz == Integer.class) {
            if (t == null)
                buf.putInt(0);
            else
                buf.putInt((Integer) t);
        } else if (clz == Short.class) {
            if (t == null)
                buf.putShort((short) 0);
            else
                buf.putShort((Short) t);
        } else if (clz == Float.class) {
            if (t == null)
                buf.putFloat(0);
            else
                buf.putFloat((Float) t);
        } else if (clz == Byte.class) {
            if (t == null)
                buf.put((byte) 0);
            else
                buf.put((Byte) t);
        } else if (clz == Long.class) {
            if (t == null)
                buf.putLong(0);
            else
                buf.putLong((Long) t);
        } else if (clz == Double.class) {
            if (t == null)
                buf.putDouble(0);
            else
                buf.putDouble((Double) t);
        } else if (clz == Character.class) {
            if (t == null)
                buf.putChar('0');
            else
                buf.putChar((Character) t);
        } else if (clz == String.class) {
            if(t == null)
                buf.putInt(0);
            else {
                byte[] bytes = ((String) t).getBytes();
                buf.putInt(bytes.length);
                for (int i = 0; i < bytes.length; i++) {
                    buf.put(bytes[i]);
                }
            }
        } else if (clz == SerInterface.class) {
            if(t == null)
                buf.putInt(0);
            else {
                buf.putInt(((SerInterface) t).size());
                buf.put(((SerInterface) t).serOut());
            }
        }
    }

    public static void serIn(){

    }
}
