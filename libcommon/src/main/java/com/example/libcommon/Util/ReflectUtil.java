package com.example.libcommon.Util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/***
 * Description:反射帮助类
 ***/
public class ReflectUtil {
    /**
     * 获取类中私有变量
     */
    public static Field findField(Class<?> clazz, String name) throws NoSuchFieldException {
        for (; clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {}
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + clazz);
    }
    /**获取类中私有变量**/

    /**
     * 获取类中私有方法
     */
    public static Method findMethod(Class<?> clazz, String name, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        for (; clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {}
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " +
                Arrays.asList(parameterTypes) + " not found in " + clazz.getCanonicalName());
    }
    /**获取类中私有方法**/

}
