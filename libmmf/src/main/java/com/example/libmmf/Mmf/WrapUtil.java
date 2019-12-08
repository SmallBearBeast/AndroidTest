package com.example.libmmf.Mmf;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WrapUtil {
    /*
        获取数组元素类型
     */
    public static Class arrayType(Object obj) {
        if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            return arrayType(array[0]);
        }
        return obj.getClass();
    }

    /*
        获取数组维数
     */
    public static int arrayLevel(Object k) {
        if (k instanceof Object[]) {
            Object[] array = (Object[]) k;
            return arrayLevel(array[0]) + 1;
        }
        return 0;
    }

    /*
        初始化单个数据类型单元
     */
    public static Object priUnit(Class clz) {
        if (clz == Integer.class) {
            return 0;
        } else if (clz == Byte.class) {
            return (byte) 0;
        } else if (clz == Short.class) {
            return (short) 0;
        } else if (clz == Character.class) {
            return (char) 0;
        } else if (clz == Float.class) {
            return 0F;
        } else if (clz == Double.class) {
            return 0D;
        } else if (clz == Long.class) {
            return 0L;
        } else if (clz == String.class) {
            return "";
        } else {
            try {
                return clz.getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
        初始化数组单元，数组单元能表示一种数组的基本信息，比如维数，类型。
        clz是元素类对象，level是数组维数
        比如arrayInit(Integer.class, 2) -> Integer[][]{{0}};
     */
    public static Object arrayUnit(Class clz, int level) {
        int[] dim = new int[level];
        for (int i = 0; i < level; i++) {
            dim[i] = 1;
        }
        Object array = Array.newInstance(clz, dim);
        Object temp = array;
        for (int i = 0; i < level; i++) {
            if (temp instanceof Object[]) {
                Object[] os = (Object[]) temp;
                temp = os[0];
                if (temp == null) {
                    os[0] = priUnit(clz);
                }
            }
        }
        return array;
    }

    /*
        包装类型数组转基本类型数组
        level是数组维数
        clz是基本类型类对象
        obj是待转化的包装类型数组
     */
    public static <V> V objToPriArray(int level, Class clz, Object obj) {
        if (obj instanceof Object[]) {
            Object[] src = (Object[]) obj;
            int[] dim = new int[level];
            dim[0] = src.length;
            Object array = Array.newInstance(clz, dim);
            level--;
            for (int i = 0; i < src.length; i++) {
                Array.set(array, i, objToPriArray(level, clz, src[i]));
            }
            return (V) array;
        } else {
            return (V) obj;
        }
    }

    /*
        基本类型数组转包装类型数组
        level是数组维数
        clz是包装类型类对象
        obj是待转化的基本类型数组
     */
    public static <V> V priToObjArray(int level, Class clz, Object obj) {
        if (level == 1) {
            int len = Array.getLength(obj);
            int[] dim = new int[level];
            dim[0] = len;
            Object array = Array.newInstance(clz, dim);
            for (int i = 0; i < len; i++) {
                Array.set(array, i, Array.get(obj, i));
            }
            return (V) array;
        }
        if (obj instanceof Object[]) {
            Object[] src = (Object[]) obj;
            int[] dim = new int[level];
            dim[0] = src.length;
            Object array = Array.newInstance(clz, dim);
            level--;
            for (int i = 0; i < src.length; i++) {
                Array.set(array, i, priToObjArray(level, clz, src[i]));
            }
            return (V) array;
        } else {
            return (V) obj;
        }
    }

    public static <K, V> Map<K, V> asMap(K[] keys, V[] vals) {
        Map<K, V> map = new HashMap<>();
        int len = keys.length;
        for (int i = 0; i < len; i++) {
            map.put(keys[i], vals[i]);
        }
        return map;
    }

    public static <T> List<T> asList(T... t){
        return Arrays.asList(t);
    }
}
