package com.example.libbase.Util;

import java.util.ArrayList;
import java.util.List;

public class GenericUtil {

    /**
     * obj转为T类型
     */
    public static <T> T toT(Object obj, Class<T> clz){
        if(obj.getClass().isAssignableFrom(clz)){
            return (T) obj;
        }
        return null;
    }
    /**obj转为T类型**/


    /**
     * list转为T范型list
     */
    public static <T> List<T> toListT(List list, Class<T> clz){
        List<T> tList = new ArrayList<>();
        for (Object obj : list) {
            tList.add(toT(obj, clz));
        }
        return tList;
    }
    /**list转为T范型list**/

}
