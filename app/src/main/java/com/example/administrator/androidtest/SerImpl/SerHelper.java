package com.example.administrator.androidtest.SerImpl;

import java.util.Collection;
import java.util.Map;

public class SerHelper {
    public static int calSerSize(String str) {
        if (str == null)
            return 4;
        return 4 + str.getBytes().length;
    }

    public static <T> int calSerSize(T[] array) {
        int size = 4;
        if (array == null)
            return size;
        for (int i = 0; i < array.length; i++) {
            size = size + serSize(array[i]);
        }
        return size;
    }

    public static <T> int calSerSize(Collection<T> collection) {
        int size = 4;
        if (collection == null)
            return size;
        for (T t : collection) {
            size = size + serSize(t);
        }
        return size;
    }

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

}
