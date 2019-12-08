package com.example.libmmf.SerImpl;



import com.example.libmmf.Mmf.WrapUtil;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.*;

public class SerHelper {
    /*
        计算t占用的字节大小
     */
    public static <T> int calSerSize(T t, Class clz) {
        int size = 0;
        if (t instanceof Integer) {
            size = 4;
        } else if (t instanceof Short) {
            size = 2;
        } else if (t instanceof Float) {
            size = 4;
        } else if (t instanceof Byte) {
            size = 1;
        } else if (t instanceof Long) {
            size = 8;
        } else if (t instanceof Double) {
            size = 8;
        } else if (t instanceof Character) {
            size = 1;
        } else if (t instanceof String) {
            String str = (String) t;
            size = 4 + str.getBytes().length;
        } else if (t instanceof SerInterface) {
            size = 4 + ((SerInterface) t).size();
        } else if (t instanceof Object[]) {
            size = 4;
            Object[] ts = (Object[]) t;
            for (int i = 0; i < ts.length; i++) {
                size = size + calSerSize(ts[i], clz);
            }
        } else if (t instanceof List) {
            size = 4;
            List list = (List) t;
            for (Object c : list) {
                size = size + calSerSize(c, clz);
            }
        } else if (t instanceof Map) {
            size = 4;
            Map map = (Map) t;
            Set<Map.Entry> set = map.entrySet();
            for (Map.Entry e : set) {
                size = size + calSerSize(e.getKey(), clz);
                size = size + calSerSize(e.getValue(), clz);
            }
        }

        if (t == null) {
            if (clz == Integer.class) {
                size = 4;
            } else if (clz == Short.class) {
                size = 2;
            } else if (clz == Float.class) {
                size = 4;
            } else if (clz == Byte.class) {
                size = 1;
            } else if (clz == Long.class) {
                size = 8;
            } else if (clz == Double.class) {
                size = 8;
            } else if (clz == Character.class) {
                size = 2;
            } else if (clz == String.class) {
                size = 4;
            } else {
                size = 4;
            }
        }
        return size;
    }

    /*
        将Map类型数据写到buf
     */
    public static <K, V> void serOut(ByteBuffer buf, Map<K, V> map, Class clzK, Class clzV) {
        if (map == null)
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
        将数据序列化到buf
     */
    public static void serOut(ByteBuffer buf, Object t, Class clz) {
        if (t instanceof Integer) {
            buf.putInt((Integer) t);
        } else if (t instanceof Short) {
            buf.putShort((Short) t);
        } else if (t instanceof Float) {
            buf.putFloat((Float) t);
        } else if (t instanceof Byte) {
            buf.put((Byte) t);
        } else if (t instanceof Long) {
            buf.putLong((Long) t);
        } else if (t instanceof Double) {
            buf.putDouble((Double) t);
        } else if (t instanceof Character) {
            buf.putChar((Character) t);
        } else if (t instanceof String) {
            byte[] bytes = ((String) t).getBytes();
            buf.putInt(bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                buf.put(bytes[i]);
            }
        } else if (t instanceof SerInterface) {
            buf.putInt(((SerInterface) t).size());
            buf.put(((SerInterface) t).outBuffer());
        } else if (t instanceof Object[]) {
            Object[] ts = (Object[]) t;
            buf.putInt(ts.length);
            for (int i = 0; i < ts.length; i++) {
                serOut(buf, ts[i], clz);
            }
        } else if (t instanceof List) {
            List list = (List) t;
            buf.putInt(list.size());
            for (Object c : list) {
                serOut(buf, c, clz);
            }
        } else if (t instanceof Map) {
            Map map = (Map) t;
            buf.putInt(map.size());
            Set<Map.Entry> set = map.entrySet();
            for (Map.Entry e : set) {
                serOut(buf, e.getKey(), clz);
                serOut(buf, e.getValue(), clz);
            }
        }

        if (t == null) {
            if (clz == Integer.class) {
                buf.putInt(0);
            } else if (clz == Short.class) {
                buf.putShort((short) 0);
            } else if (clz == Float.class) {
                buf.putFloat((0F));
            } else if (clz == Byte.class) {
                buf.put((byte) 0);
            } else if (clz == Long.class) {
                buf.putLong(0L);
            } else if (clz == Double.class) {
                buf.putDouble(0D);
            } else if (clz == Character.class) {
                buf.putChar((char) 0);
            } else {
                buf.putInt(0);
            }
        }
    }

    public static Object serIn(ByteBuffer buf, Object t, Class clz) {
        if (t instanceof Integer) {
            return buf.getInt();
        } else if (t instanceof Short) {
            return buf.getShort();
        } else if (t instanceof Float) {
            return buf.getFloat();
        } else if (t instanceof Byte) {
            return buf.get();
        } else if (t instanceof Long) {
            return buf.getLong();
        } else if (t instanceof Double) {
            return buf.getDouble();
        } else if (t instanceof Character) {
            return buf.getChar();
        } else if (t instanceof String) {
            int len = buf.getInt();
            byte[] bytes = new byte[len];
            for (int i = 0; i < len; i++) {
                bytes[i] = buf.get();
            }
            return new String(bytes);
        } else if (t instanceof SerInterface) {
            try {
                SerInterface ser = (SerInterface) t;
                int size = buf.getInt();
                if (size > 0) {
                    ser.in(buf);
                    return ser;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (t instanceof Object[]) {
            int len = buf.getInt();
            if (len > 0) {
                Object[] ts = (Object[]) t;
                int[] dim = new int[WrapUtil.arrayLevel(t)];
                dim[0] = len;
                Object[] result = (Object[]) Array.newInstance(clz, dim);
                for (int i = 0; i < len; i++) {
                    result[i] = serIn(buf, ts[0], clz);
                }
                return result;
            }
        } else if (t instanceof List) {
            List list = (List) t;
            int len = buf.getInt();
            if (len > 0) {
                Object[] os = new Object[len];
                Object unit = list.get(0);
                for (int i = 0; i < len; i++) {
                    os[i] = serIn(buf, unit, clz);
                }
                List otherList = Arrays.asList(os);
                return otherList;
            }
        } else if (t instanceof Map) {
            Map map = (Map) t;
            int len = buf.getInt();
            if (len > 0) {
                Set<Map.Entry> set = map.entrySet();
                Map result = new HashMap();
                for (Map.Entry e : set) {
                    Object objKey = e.getKey();
                    Object objVal = e.getValue();
                    for (int i = 0; i < len; i++) {
                        result.put(serIn(buf, objKey, clz), serIn(buf, objVal, clz));
                    }
                    return result;
                }
            }
        }
        return null;
    }
}
